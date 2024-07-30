package com.shotty.shotty.domain.auth.exception.custom_exception.user;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException(String message){
        super(message);
    }
}
