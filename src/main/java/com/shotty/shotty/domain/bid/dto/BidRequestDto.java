package com.shotty.shotty.domain.bid.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BidRequestDto(
        @Schema(description = "지원 내역에 대한 식별키", example = "1")
        @NotNull(message = "입찰을 위해서는 지원 내역에 대한 식별키가 반드시 필요합니다.")
        Long applyId
) {
}
