package org.kainos.ea.exception;

public class FailedToGenerateTokenException extends Exception {
    @Override
    public String getMessage() {
        return "Failed to generate token";
    }
}
