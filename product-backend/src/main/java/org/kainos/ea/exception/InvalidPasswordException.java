package org.kainos.ea.exception;

public class InvalidPasswordException extends Throwable {
    @Override
    public String getMessage() {
        return "Provided password is invalid";
    }
}
