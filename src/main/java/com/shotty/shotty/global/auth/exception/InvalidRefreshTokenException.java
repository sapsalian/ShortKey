package com.shotty.shotty.global.auth.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException() {
    }

    public InvalidRefreshTokenException(String message) {
            super(message);
    }
}

