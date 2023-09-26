package org.kainos.ea.exception;

public class FailedToGetProductsException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to get products from the database";
    }
}
