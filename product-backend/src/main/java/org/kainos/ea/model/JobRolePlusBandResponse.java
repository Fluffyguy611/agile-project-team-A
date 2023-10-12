package org.kainos.ea.model;

public class JobRolePlusBandResponse {
    private int id;
    private String name;
    private String description;
    private String sharePointLink;
    private int bandId;
    private String bandName;
    private int bandLevel;

    public JobRolePlusBandResponse(int id, String name, String description, String sharePointLink, int bandId, String bandName, int bandLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sharePointLink = sharePointLink;
        this.bandId = bandId;
        this.bandName = bandName;
        this.bandLevel = bandLevel;
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

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public int getBandLevel() {
        return bandLevel;
    }

    public void setBandLevel(int bandLevel) {
        this.bandLevel = bandLevel;
    }
}