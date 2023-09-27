package org.kainos.ea.cli;
public class JobRoleSingleViewA {

        private int Id;
        private String Name;
        private String Description;
        private String SharePointLink;

    public JobRoleSingleViewA(int id, String name, String description, String sharePointLink) {
        Id = id;
        Name = name;
        Description = description;
        SharePointLink = sharePointLink;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSharePointLink() {
        return SharePointLink;
    }

    public void setSharePointLink(String sharePointLink) {
        SharePointLink = sharePointLink;
    }
}
