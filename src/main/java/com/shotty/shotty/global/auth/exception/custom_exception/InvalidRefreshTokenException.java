package com.shotty.shotty.global.auth.exception.custom_exception;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException() {
    }

    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}