package org.kainos.ea.exception;

public class FailedToGetAllJobRolesException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get job all job roles";
    }
}
