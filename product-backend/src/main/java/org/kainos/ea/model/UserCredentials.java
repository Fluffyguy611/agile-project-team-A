package org.kainos.ea.model;

public class UserCredentials {
    private String token;
    private int roleId;

    public UserCredentials(String token, int roleId) {
        setToken(token);
        setRoleId(roleId);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
