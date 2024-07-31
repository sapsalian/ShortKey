package com.shotty.shotty.domain.user.exception.custom_exception;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException(String message){
        super(message);
    }
}
