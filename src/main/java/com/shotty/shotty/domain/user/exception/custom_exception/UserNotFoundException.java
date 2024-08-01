package com.shotty.shotty.domain.user.exception.custom_exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {}

    public UserNotFoundException(String message) {
        super(message);
    }
}
