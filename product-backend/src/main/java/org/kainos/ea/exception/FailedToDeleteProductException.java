package org.kainos.ea.exception;

public class FailedToDeleteProductException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to delete product";
    }
}
