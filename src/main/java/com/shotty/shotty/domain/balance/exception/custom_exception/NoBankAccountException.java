package com.shotty.shotty.domain.balance.exception.custom_exception;

public class NoBankAccountException extends RuntimeException {
    public NoBankAccountException() {
        super("계좌가 등록되어 있지 않습니다. 계좌를 먼저 등록해주세요.");
    }

    public NoBankAccountException(String message) {
        super(message);
    }
}
