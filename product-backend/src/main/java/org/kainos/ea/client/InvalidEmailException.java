package org.kainos.ea.client;

public class InvalidEmailException extends Throwable {
    @Override
    public String getMessage(){
        return "Provided email is invalid";
    }
}
