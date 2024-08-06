package com.shotty.shotty.domain.bid.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BidRequestDto(
        Long applyId,

        Long userId
) {
}
