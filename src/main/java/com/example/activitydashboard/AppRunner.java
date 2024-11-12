package com.example.activitydashboard;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class AppRunner implements CommandLineRunner {

    private final GitHubLookupService gitHubLookupService;

    @Value("${env.serverUrl}")
    private String serverUrl;
    @Value("${env.owner}")
    private String owner;
    @Value("${env.repo}")
    private String repo;
    @Value("${env.apiKey}")
    private String apiKey;
    @Value("${env.pageCount}")
    private int pageCount;
    @Value("${env.index}")
    private String index;

    /**
     * Constructor method, defaults serverUrl to be changed later if passed in as an argument
     */
    public AppRunner(GitHubLookupService gitHubLookupService) {

        this.gitHubLookupService = gitHubLookupService;
    }

    /**
     * run method handles bulk of execution, calls gitHubLookupService to retrieve data and builds restClient to index data
     * @param args array of command line arguments, will contain owner, repo and apiKey
     * @throws InterruptedException If gitHubLookupService methods get interrupted when retrieving api calls
     */
    @Override
    public void run(String... args) throws InterruptedException, IOException, ExecutionException {
        // If values are passed in as arguments, sets variable equal to them otherwise uses the defaults
        if (args.length == 5) {
            owner = args[0];
            repo = args[1];
            apiKey = args[2];
            serverUrl = args[3];
            pageCount = Integer.parseInt(args[4]);
        }

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

        for (int currentPage = 1; currentPage < pageCount+1; currentPage++){
            // Sends a request for each api return
            // I'm using separate methods and classes for each request as I believe that will be easiest to adapt instead of using one data class
            CompletableFuture<GitCommitData[]> GitCommits = gitHubLookupService.findCommits(owner, repo, currentPage);
            CompletableFuture<GitPullsData[]> GitPulls = gitHubLookupService.findPulls(owner, repo, currentPage);
            CompletableFuture<GitIssuesData[]> GitIssues = gitHubLookupService.findIssues(owner, repo, currentPage);
            CompletableFuture<GitReleasesData[]> GitReleases = gitHubLookupService.findReleases(owner, repo, currentPage);

            // Wait until they are all done
            CompletableFuture.allOf(GitCommits, GitPulls, GitIssues, GitReleases).join();

            // Send api returns to elastic search index methods
            ElasticIndex.indexCommits(client, GitCommits.get(), index);
            ElasticIndex.indexPulls(client, GitPulls.get(), index);
            ElasticIndex.indexIssues(client, GitIssues.get(), index);
            ElasticIndex.indexReleases(client, GitReleases.get(), index);
        }
        restClient.close();
    }

}