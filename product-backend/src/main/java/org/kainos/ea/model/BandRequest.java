package org.kainos.ea.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BandRequest {
    private String name;
    private int level;

    @JsonCreator
    public BandRequest(
            @JsonProperty("name") String name,
            @JsonProperty("level") int level) {
        setName(name);
        setLevel(level);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
