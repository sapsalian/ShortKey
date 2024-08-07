package com.shotty.shotty.domain.apply.exception.custom_exception;

public class ExpiredPostException extends RuntimeException {
    public ExpiredPostException() {}
    public ExpiredPostException(String message) {
        super(message);
    }
}
