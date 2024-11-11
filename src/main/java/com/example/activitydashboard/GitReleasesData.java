package com.example.activitydashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The GitReleaseData class is used with Jackson to create an object containing variables from GitHub api responses.
 * Ignore properties that are not set within constructor
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GitReleasesData {
    /**
     * String id from the GitHub api response, representing a unique id
     * String tag_name to be added to json return
     */
    private String id;
    private String tag_name;
    private final String type = "Release";
    private String created_at;

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return "Release";
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * Overrides the toStringMethod()
     * @return String with Json formatting to be indexed with elastic
     */

    @Override
    public String toString() {
        return "Id: " + id + ", Tag: " + tag_name + ", Type: " + type;
    }

}
