package com.shotty.shotty.global.common.exception.custom_exception;

public class NoSuchResourcException extends RuntimeException {
    public NoSuchResourcException() {
        super("해당 resource가 존재하지 않습니다.");
    }

    public NoSuchResourcException(String message) {
        super(message);
    }

    public NoSuchResourcException(Throwable cause) {
        super(cause);
    }

    public NoSuchResourcException(String message, Throwable cause) {
        super(message, cause);
    }
}
