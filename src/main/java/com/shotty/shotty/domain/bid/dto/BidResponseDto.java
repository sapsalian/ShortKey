package com.shotty.shotty.domain.bid.dto;

import com.shotty.shotty.domain.bid.domain.Bid;
import io.swagger.v3.oas.annotations.media.Schema;

public record BidResponseDto(
        @Schema(description = "입찰 내역에 대한 식별키", example = "1")
        Long id,

        @Schema(description = "입찰 완료된 지원에 대한 식별키", example = "3")
        Long applyId
) {
    public static BidResponseDto from(Bid bid) {
        return new BidResponseDto(
                bid.getId(),
                bid.getApply().getId()
        );
    }
}
