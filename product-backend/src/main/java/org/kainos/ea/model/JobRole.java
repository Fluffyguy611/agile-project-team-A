package org.kainos.ea.model;


public class JobRole {
    private int id;
    private String name;
    private String description;
    private String sharePointLink;
    private int capabilityId;

    public JobRole(int id, String roleName, String description, String sharePointLink, int capabilityId) {
        this.id = id;
        this.name = roleName.trim();
        this.description = description.trim();
        this.sharePointLink = sharePointLink.trim();
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

    public int getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(int capabilityId) {
        this.capabilityId = capabilityId;
    }
}
