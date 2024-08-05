package com.shotty.shotty.global.common.exception.custom_exception;

public class NoSuchSortFieldException extends RuntimeException {
    public NoSuchSortFieldException() {
        super("요청하신 sort 쿼리 파라미터 값과 일치하는 필드가 존재하지 않습니다.");
    }

    public NoSuchSortFieldException(String message) {
        super(message);
    }

    public NoSuchSortFieldException(Throwable cause) {
        super(cause);
    }

    public NoSuchSortFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
