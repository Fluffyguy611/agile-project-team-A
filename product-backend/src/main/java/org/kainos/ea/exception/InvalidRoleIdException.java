package org.kainos.ea.exception;

public class InvalidRoleIdException extends Throwable {
    @Override
    public String getMessage() {
        return "Provided role id is invalid";
    }
}
