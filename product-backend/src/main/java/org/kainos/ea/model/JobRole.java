package org.kainos.ea.model;


public class JobRole {
    private int id;
    private String name;
    private String description;
    private String sharePointLink;
    private int capabilityId;
    private int bandId;
    private String bandName;
    private int bandLevel;


    public JobRole(int id, String name, String description, String sharePointLink, int capabilityId, int bandId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sharePointLink = sharePointLink;
        this.capabilityId = capabilityId;
        this.bandId = bandId;
    }

    public JobRole(int id, String name, String description, String sharePointLink, int bandId, int capabilityId, String bandName, int bandLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sharePointLink = sharePointLink;
        this.capabilityId = capabilityId;
        this.bandId = bandId;
        this.bandName = bandName;
        this.bandLevel = bandLevel;
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

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

}


