package org.kainos.ea.client;

public class InvalidUserException extends Throwable {
    @Override
    public String getMessage(){
        return "Provided user data invalid";
    }
}
