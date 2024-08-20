package com.shotty.shotty.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AccountCreateReqDto(
        @Schema(description = "출금 계좌 은행",example = "대구은행")
        @NotNull(message = "출금 계좌 은행은 반드시 등록해야 합니다.")
        String bank,

        @Schema(description = "출금 계좌 번호",example = "111111-11-111111")
        @NotNull(message = "출금 계좌 번호는 반드시 등록해야 합니다.")
        String account
) {
}
