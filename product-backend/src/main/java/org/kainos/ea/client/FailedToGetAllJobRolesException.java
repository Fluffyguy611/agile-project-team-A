package org.kainos.ea.client;

public class FailedToGetAllJobRolesException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get job all job roles";
    }
}
