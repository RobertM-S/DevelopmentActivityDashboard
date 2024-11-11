package com.example.activitydashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The GitPullsData class is used with Jackson to create an object containing variables from GitHub api responses.
 * Ignore properties that are not set within constructor
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GitPullsData {
    /**
     * String id from the GitHub api response, representing a unique id
     * String title, body, created_at, updated_at are fields to be added to json return
     */
    private String id;
    private String title;
    private String body;
    private String created_at;
    private String updated_at;
    private final String type = "Pull";

    /**
     * Get and Set methods for each variable
     */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return "Pull";
    }

    /**
     * Overrides the toStringMethod()
     * @return String with Json formatting to be indexed with elastic
     */

    @Override
    public String toString() {
        return "Id: " + id + ", title: " + title + ", body: " + body + ", created_at: " + created_at + ", updated_at: ";
    }

}
