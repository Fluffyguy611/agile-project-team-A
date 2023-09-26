package org.kainos.ea.exception;

public class ProductDoesNotExistException extends Throwable {
    @Override
    public String getMessage() {
        return "Product does not exist";
    }
}
