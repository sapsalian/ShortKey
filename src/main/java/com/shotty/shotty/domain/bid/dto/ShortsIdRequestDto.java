package com.shotty.shotty.domain.bid.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShortsIdRequestDto(
        @Schema(description = "게시한 쇼츠 영상의 id", example = "hs7c7X_sqW0")
        String shortsId
) {
}
