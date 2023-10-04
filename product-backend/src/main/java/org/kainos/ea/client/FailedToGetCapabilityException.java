package org.kainos.ea.client;

public class FailedToGetCapabilityException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get capability";
    }
}
