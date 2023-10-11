package org.kainos.ea.exception;

public class FailedToCreateCapabilityLeadException extends Exception {
    @Override
    public String getMessage() {
        return "Failed to create capability";
    }
}
