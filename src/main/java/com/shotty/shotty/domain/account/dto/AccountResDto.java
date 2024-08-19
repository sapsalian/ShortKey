package com.shotty.shotty.domain.account.dto;

import com.shotty.shotty.domain.account.domain.Account;
import io.swagger.v3.oas.annotations.media.Schema;

public record AccountResDto(
        @Schema(description = "출금 계좌 은행",example = "대구은행")
        String bank,

        @Schema(description = "출금 계좌 번호",example = "111111-11-111111")
        String account
) {
    public static AccountResDto from(Account account) {
        return new AccountResDto(
                account.getBank(),
                account.getAccount()
        );
    }
}
