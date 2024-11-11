package com.example.activitydashboard;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class AppRunner implements CommandLineRunner {

    private final GitHubLookupService gitHubLookupService;

    private String serverUrl;
    private String owner;
    private String repo;
    private String apiKey;

    /**
     * Constructor method, defaults serverUrl to be changed later if passed in as an argument
     */
    public AppRunner(GitHubLookupService gitHubLookupService) {

        this.gitHubLookupService = gitHubLookupService;
        this.serverUrl = "http://localhost:9200";
        this.owner = "hashicorp";
        this.repo = "terraform";
        // Example ApiKey, not valid
        this.apiKey = "";
    }

    /**
     * run method handles bulk of execution, calls gitHubLookupService to retrieve data and builds restClient to index data
     * @param args array of command line arguments, will contain owner, repo and apiKey
     * @throws InterruptedException If gitHubLookupService methods get interrupted when retrieving api calls
     */
    @Override
    public void run(String... args) throws InterruptedException, IOException, ExecutionException {
        // If values are passed in as arguments, sets variable equal to them otherwise uses the defaults
        if (args.length == 3) {
            owner = args[0];
            repo = args[1];
            apiKey = args[2];
        }
        // Sends a request for each api return
        // I'm using separate methods and classes for each request as I believe that will be easiest to adapt instead of using one data class
        CompletableFuture<GitCommitData[]> GitCommits = gitHubLookupService.findCommits(owner, repo);
        CompletableFuture<GitPullsData[]> GitPulls = gitHubLookupService.findPulls(owner, repo);
        CompletableFuture<GitIssuesData[]> GitIssues = gitHubLookupService.findIssues(owner, repo);
        CompletableFuture<GitReleasesData[]> GitReleases = gitHubLookupService.findReleases(owner, repo);

        // Wait until they are all done
        CompletableFuture.allOf(GitCommits, GitPulls, GitIssues).join();
        // Creates the restClient to connect to elastic search local host
        RestClient restClient = RestClient
                .builder(HttpHost.create(serverUrl))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + apiKey)
                })
                .build();
        // Uses Jackson to parse inputs such as Json
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        ElasticIndex.indexCommits(client, GitCommits.get());
        ElasticIndex.indexPulls(client, GitPulls.get());
        ElasticIndex.indexIssues(client, GitIssues.get());
        ElasticIndex.indexReleases(client, GitReleases.get());
        restClient.close();
    }

}