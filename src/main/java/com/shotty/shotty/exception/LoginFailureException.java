package com.shotty.shotty.exception;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException(String message){
        super(message);
    }
}
