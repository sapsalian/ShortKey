package com.shotty.shotty.global.auth.exception.custom_exception;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException(String message){
        super(message);
    }
}
