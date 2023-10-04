package org.kainos.ea.exception;

public class FailedToGetJobRoleException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get job role";
    }
}
