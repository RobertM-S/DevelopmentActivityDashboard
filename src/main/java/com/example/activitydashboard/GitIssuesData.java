package com.example.activitydashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The GitIssuesData class is used with Jackson to create an object containing variables from GitHub api responses.
 * Ignore properties that are not set within constructor
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GitIssuesData {
    /**
     * String id from the GitHub api response, representing a unique id
     * String title, created_at are fields to be added to json return
     */
    private String id;
    private String title;
    private String created_at;
    private final String type = "Issue";

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return "Issue";
    }

    /**
     * Overrides the toStringMethod()
     * @return String with Json formatting to be indexed with elastic
     */

    @Override
    public String toString() {
        return "Id: " + id + ", Title: " + title + ", Created At: " + created_at + ", Type: " + type;
    }

}
