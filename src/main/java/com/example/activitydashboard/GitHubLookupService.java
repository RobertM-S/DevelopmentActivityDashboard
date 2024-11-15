package com.example.activitydashboard;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GitHubLookupService {

    /**
     * RestTemplate used to perform http requests
     */
    private final RestTemplate restTemplate;
    private final int perPage;

    /**
     * GitHubLookupService constructor
     * @param restTemplateBuilder builds restTemplate to perform http requests
     */
    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.perPage = 30;
    }

    /**
     * Async method that makes a GitHub api request and maps results to GitCommitData class
     * @param owner String representing GitHub repository owner
     * @param repo String representing GitHub repository
     * @return Object array containing GitHub api response mapped to GitCommitData class
     * @throws InterruptedException thrown if thread is interrupted when waiting for response from GitHub api
     */
    @Async
    public CompletableFuture<GitCommitData[]> findCommits(String owner, String repo, int page) throws InterruptedException {
        String url = String.format("https://api.github.com/repos/%s/%s/commits?per_page=%s&page=%s", owner, repo, perPage, page);
        GitCommitData[] response = restTemplate.getForEntity(url, GitCommitData[].class).getBody();
        return CompletableFuture.completedFuture(response);
    }

    /**
     * Async method that makes a GitHub api request and maps results to GitPullsData class
     * @param owner String representing GitHub repository owner
     * @param repo String representing GitHub repository
     * @return Object array containing GitHub api response mapped to GitPullsData class
     * @throws InterruptedException thrown if thread is interrupted when waiting for response from GitHub api
     */
    @Async
    public CompletableFuture<GitPullsData[]> findPulls(String owner, String repo, int page) throws InterruptedException {
        String url = String.format("https://api.github.com/repos/%s/%s/pulls?per_page=%s&page=%s", owner, repo, perPage, page);
        GitPullsData[] response = restTemplate.getForEntity(url, GitPullsData[].class).getBody();
        return CompletableFuture.completedFuture(response);
    }

    /**
     * Async method that makes a GitHub api request and maps results to GitIssuesData class
     * @param owner String representing GitHub repository owner
     * @param repo String representing GitHub repository
     * @return Object array containing GitHub api response mapped to GitIssuesData class
     * @throws InterruptedException thrown if thread is interrupted when waiting for response from GitHub api
     */
    @Async
    public CompletableFuture<GitIssuesData[]> findIssues(String owner, String repo, int page) throws InterruptedException {
        String url = String.format("https://api.github.com/repos/%s/%s/issues?per_page=%s&page=%s", owner, repo, perPage, page);
        GitIssuesData[] response = restTemplate.getForEntity(url, GitIssuesData[].class).getBody();
        return CompletableFuture.completedFuture(response);
    }

    /**
     * Async method that makes a GitHub api request and maps results to GitReleasesData class
     * @param owner String representing GitHub repository owner
     * @param repo String representing GitHub repository
     * @return Object array containing GitHub api response mapped to GitReleasesData class
     * @throws InterruptedException thrown if thread is interrupted when waiting for response from GitHub api
     */
    @Async
    public CompletableFuture<GitReleasesData[]> findReleases(String owner, String repo, int page) throws InterruptedException {
        String url = String.format("https://api.github.com/repos/%s/%s/releases?per_page=%s&page=%s", owner, repo, perPage, page);
        GitReleasesData[] response = restTemplate.getForEntity(url, GitReleasesData[].class).getBody();
        return CompletableFuture.completedFuture(response);
    }

}