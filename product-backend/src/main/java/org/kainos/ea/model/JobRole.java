package org.kainos.ea.model;


public class JobRole {
    private int id;
    private String name;
    private String description;
    private String sharePointLink;

    public JobRole(int id, String roleName, String description, String sharePointLink) {
        this.id = id;
        this.name = roleName.trim();
        this.description = description.trim();
        this.sharePointLink = sharePointLink.trim();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
