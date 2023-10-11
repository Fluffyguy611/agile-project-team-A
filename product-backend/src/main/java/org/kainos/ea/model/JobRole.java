package org.kainos.ea.model;


public class JobRole {
    private int id;
    private String name;
    private String description;
    private String sharePointLink;
    private int capabilityId;


    public JobRole(int id, String name, String description, String sharePointLink, int capabilityId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sharePointLink = sharePointLink;
        this.capabilityId = capabilityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCapabilitId() {
        return capabilityId;
    }

    public void setCapabilitId(int capabilitId) {
        this.capabilityId = capabilitId;
    }
}


