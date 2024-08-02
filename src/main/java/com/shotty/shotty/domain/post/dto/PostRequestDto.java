package com.shotty.shotty.domain.post.dto;

import java.time.LocalDateTime;

public record PostRequestDto(
        String title,
        String content,
        int price,
        int extraPrice,
        LocalDateTime endDate
) {
}
