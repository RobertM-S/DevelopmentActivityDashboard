package com.example.activitydashboard;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;

import java.io.IOException;

public class ElasticIndex {
    /**
     * Method that sends commit data to the ElasticsearchClient
     * @param client Connection to elastic search local host
     * @param gitData Object array containing commit response from GitHub api
     * @throws IOException
     */
    public static void indexCommits(ElasticsearchClient client, GitCommitData[] gitData) throws IOException {
        for (GitCommitData data: gitData) {
            IndexResponse response = client.index(i -> i
                    .index("gitterra")
                    .id(data.getSha())
                    .document(data));        }
    }
    /**
     * Method that sends pull data to the ElasticsearchClient
     * @param client Connection to elastic search local host
     * @param gitData Object array containing pull response from GitHub api
     * @throws IOException
     */
    public static void indexPulls(ElasticsearchClient client, GitPullsData[] gitData) throws IOException {
        for (GitPullsData data: gitData) {
            IndexResponse response = client.index(i -> i
                    .index("gitterra")
                    .id(data.getId())
                    .document(data));        }
    }
    /**
     * Method that sends issue data to the ElasticsearchClient
     * @param client Connection to elastic search local host
     * @param gitData Object array containing issue response from GitHub api
     * @throws IOException
     */
    public static void indexIssues(ElasticsearchClient client, GitIssuesData[] gitData) throws IOException {
        for (GitIssuesData data: gitData) {
            IndexResponse response = client.index(i -> i
                    .index("gitterra")
                    .id(data.getId())
                    .document(data));        }
    }
    /**
     * Method that sends release data to the ElasticsearchClient
     * @param client Connection to elastic search local host
     * @param gitData Object array containing release response from GitHub api
     * @throws IOException
     */
    public static void indexReleases(ElasticsearchClient client, GitReleasesData[] gitData) throws IOException {
        for (GitReleasesData data: gitData) {
            IndexResponse response = client.index(i -> i
                    .index("gitterra")
                    .id(data.getId())
                    .document(data));        }
    }
}
