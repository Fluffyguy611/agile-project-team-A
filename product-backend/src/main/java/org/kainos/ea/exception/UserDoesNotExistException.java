package org.kainos.ea.exception;

public class UserDoesNotExistException extends Throwable {
    @Override
    public String getMessage() {
        return "User does not exist";
    }
}