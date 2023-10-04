package org.kainos.ea.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRoleRequest {
    private String name;
    private String description;
    private String sharePointLink;

    @JsonCreator
    public JobRoleRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("sharePointLink") String sharePointLink) {
        setName(name);
        setDescription(description);
        setSharePointLink(sharePointLink);
    }

    public String getName() {
        return name.trim();
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getDescription() {
        return description.trim();
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public String getSharePointLink() {
        return sharePointLink.trim();
    }

    public void setSharePointLink(String sharePointLink) {
        this.sharePointLink = sharePointLink.trim();
    }
}
