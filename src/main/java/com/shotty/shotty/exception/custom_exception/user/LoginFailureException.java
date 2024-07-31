package com.shotty.shotty.exception.custom_exception.user;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException(String message){
        super(message);
    }
}
