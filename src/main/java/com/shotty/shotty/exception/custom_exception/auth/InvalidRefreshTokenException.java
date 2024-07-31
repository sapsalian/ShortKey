package com.shotty.shotty.exception.custom_exception.auth;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException() {
    }

    public InvalidRefreshTokenException(String message) {
            super(message);
    }
}

