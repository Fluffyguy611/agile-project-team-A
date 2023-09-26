package org.kainos.ea.exception;

public class FailedToCreateProductException extends Throwable {
    public FailedToCreateProductException() {
    }

    public FailedToCreateProductException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Failed to create product";
    }
}
