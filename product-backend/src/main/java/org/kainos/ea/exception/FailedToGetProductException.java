package org.kainos.ea.exception;

public class FailedToGetProductException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get product from the database";
    }
}
