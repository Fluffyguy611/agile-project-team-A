package org.kainos.ea.exception;

public class InvalidEmailException extends Throwable {
    @Override
    public String getMessage() {
        return "Provided email is invalid";
    }
}
