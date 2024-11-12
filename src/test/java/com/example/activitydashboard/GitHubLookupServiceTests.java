package com.example.activitydashboard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class GitHubLookupServiceTests {

	// Injects from GitHubLookupService.java
	@Autowired
	private GitHubLookupService gitHubLookupService;

	@Test
	void GitHubLookupNotNull() throws Exception {
		assertThat(gitHubLookupService).isNotNull();
		// Using Terraform public repo
		String owner = "hashicorp";
		String repo = "terraform";
		// Api call for each data type
		CompletableFuture<GitCommitData[]> GitCommits = gitHubLookupService.findCommits(owner, repo, 1);
		CompletableFuture<GitPullsData[]> GitPulls = gitHubLookupService.findPulls(owner, repo, 1);
		CompletableFuture<GitIssuesData[]> GitIssues = gitHubLookupService.findIssues(owner, repo, 1);
		CompletableFuture<GitReleasesData[]> GitReleases = gitHubLookupService.findReleases(owner, repo, 1);

		// Wait for all async methods to finish
		CompletableFuture.allOf(GitCommits, GitPulls, GitIssues, GitReleases).join();

		// Assert that return has results
		assertThat(GitCommits.get()).isNotNull();
		assertThat(GitPulls.get()).isNotNull();
		assertThat(GitIssues.get()).isNotNull();
		assertThat(GitReleases.get()).isNotNull();
	}

}
