package org.kainos.ea.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRoleRequest {
    private String name;
    private String description;
    private String sharePointLink;
    private int capabilityId;
    private int bandId;


    @JsonCreator
    public JobRoleRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("sharePointLink") String sharePointLink,
            @JsonProperty("capabilityId") int capabilityId,
            @JsonProperty("bandId") int bandId) {
        setName(name);
        setDescription(description);
        setSharePointLink(sharePointLink);
        setCapabilityId(capabilityId);
        setBandId(bandId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSharePointLink() {
        return sharePointLink;
    }

    public void setSharePointLink(String sharePointLink) {
        this.sharePointLink = sharePointLink;
    }

    public int getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(int capabilityId) {
        this.capabilityId = capabilityId;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

}


