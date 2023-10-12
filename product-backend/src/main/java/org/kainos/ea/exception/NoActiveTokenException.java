package org.kainos.ea.exception;

public class NoActiveTokenException extends Throwable {
    @Override
    public String getMessage() {
        return "No active token";
    }
}
