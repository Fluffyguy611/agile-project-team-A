package org.kainos.ea.client;

public class CapabilityDoesNotExistException extends Throwable {
    @Override
    public String getMessage() {
        return "The capability does not exist";
    }
}
