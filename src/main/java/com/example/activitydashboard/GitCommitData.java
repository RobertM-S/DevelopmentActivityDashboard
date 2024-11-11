package com.example.activitydashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The GitCommitData class is used with Jackson to create an object containing variables from GitHub api responses.
 * Ignore properties that are not set within constructor
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class GitCommitData {
    /**
     * String Sha from the GitHub api response, representing a unique id
     */
    private String sha;
    private int comment_count;

    public String getSha() {
        return sha;
    }
    public void setSha(String sha) {
        this.sha = sha;
    }
    public int getComment_count() {
        return comment_count;
    }
    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getType() {
        return "Commit";
    }

    /**
     * Overrides the toStringMethod()
     * @return String with Json formatting to be indexed with elastic
     */

    @Override
    public String toString() {
        return "Id: " + sha + ", comment_count: " + comment_count;
    }

}
