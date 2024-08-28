package com.shotty.shotty.domain.balance.exception.custom_exception;

public class BalanceNotFoundException extends RuntimeException {
    public BalanceNotFoundException() {super("잔고 정보가 없습니다");}
    public BalanceNotFoundException(String message) {super(message);}
}
