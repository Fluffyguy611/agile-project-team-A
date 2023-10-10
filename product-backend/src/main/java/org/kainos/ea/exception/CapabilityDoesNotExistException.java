package org.kainos.ea.exception;

public class CapabilityDoesNotExistException extends Throwable {
    @Override
    public String getMessage() {
        return "The capability does not exist";
    }
}
