package com.shotty.shotty.exception.custom_exception.auth;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException(String message){
        super(message);
    }
}
