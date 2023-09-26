package org.kainos.ea.exception;

public class FailedToUpdateProductException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to update product";
    }
}
