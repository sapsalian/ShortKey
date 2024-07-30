package com.shotty.shotty.domain.auth.exception.custom_exception.user;

public class UserIdDuplicateException extends RuntimeException {

    public UserIdDuplicateException() {
        super("이미 존재하는 사용자입니다.");
    }

    public UserIdDuplicateException(String message) {
        super(message);
    }

    public UserIdDuplicateException(Throwable cause) {
        super(cause);
    }

    public UserIdDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
