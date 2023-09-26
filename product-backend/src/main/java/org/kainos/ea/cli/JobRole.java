package org.kainos.ea.cli;

public class JobRole {
    private int roleId;
    private String name;
    private String description;
    private String sharePointLink;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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


    public JobRole(int roleId, String name, String description, String sharePointLink) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
        this.sharePointLink = sharePointLink;
    }


}
