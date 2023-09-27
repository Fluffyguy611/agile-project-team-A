package org.kainos.ea.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String email;
    private String password;
    private int roleId;

    @JsonCreator
    public User(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("roleId") int roleId) {
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
