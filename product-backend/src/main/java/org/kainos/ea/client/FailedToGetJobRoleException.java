package org.kainos.ea.client;

public class FailedToGetJobRoleException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get job role";
    }
}
