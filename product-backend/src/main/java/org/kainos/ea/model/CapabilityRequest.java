package org.kainos.ea.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CapabilityRequest {
    private String capability;
    private String name;
    private String photo;
    private String message;


    @JsonCreator
    public CapabilityRequest(
            @JsonProperty("capability") String capability,
            @JsonProperty("name") String name,
            @JsonProperty("photo") String photo,
            @JsonProperty("message") String message) {
        setCapability(capability);
        setName(name);
        setPhoto(photo);
        setMessage(message);
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
