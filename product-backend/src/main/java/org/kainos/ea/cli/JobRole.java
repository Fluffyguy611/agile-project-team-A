package org.kainos.ea.cli;

public class JobRole {

    private int id;
    private String name;
    private String description;
    private String sharePointLink;

    public JobRole(int id, String name, String description, String sharePointLink) {
        id = id;
        name = name;
        description = description;
        sharePointLink = sharePointLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getSharePointLink() {
        return sharePointLink;
    }

    public void setSharePointLink(String sharePointLink) {
        sharePointLink = sharePointLink;
    }
}
