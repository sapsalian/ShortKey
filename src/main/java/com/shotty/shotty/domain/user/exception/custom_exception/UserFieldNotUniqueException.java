package com.shotty.shotty.domain.user.exception.custom_exception;

public class UserFieldNotUniqueException extends RuntimeException {

    public UserFieldNotUniqueException() {
        super("이미 존재하는 사용자입니다.");
    }

    public UserFieldNotUniqueException(String message) {
        super(message);
    }

    public UserFieldNotUniqueException(Throwable cause) {
        super(cause);
    }

    public UserFieldNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
