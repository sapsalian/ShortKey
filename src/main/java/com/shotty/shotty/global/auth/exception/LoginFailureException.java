package com.shotty.shotty.global.auth.exception;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException(String message){
        super(message);
    }
}
