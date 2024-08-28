package com.shotty.shotty.domain.balance.dto;

import com.shotty.shotty.domain.balance.domain.Balance;
import com.shotty.shotty.domain.user.domain.User;

public record BalanceResDto(
        Long userId,
        Integer balance
) {
    public static BalanceResDto from(Balance balance) {
        return new BalanceResDto(balance.getId(), balance.getBalance());
    }
}
