package org.kainos.ea.model;

public class Capability {
    private int id;
    private String capability;
    private String name;
    private String photo;
    private String message;

    public Capability(int id, String capability, String name, String photo, String message) {
        setId(id);
        setCapability(capability);
        setName(name);
        setPhoto(photo);
        setMessage(message);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
