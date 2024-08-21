package com.shotty.shotty.domain.balance.dto;

import com.shotty.shotty.domain.user.domain.User;

public record BalanceResDto(
        Long userId,
        int balance
) {
    public static BalanceResDto from(User user) {
        return new BalanceResDto(user.getId(), user.getBalance());
    }
}
