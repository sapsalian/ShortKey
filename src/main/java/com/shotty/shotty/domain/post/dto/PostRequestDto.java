package com.shotty.shotty.domain.post.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PostRequestDto(
        @NotNull(message = "공고 제목은 필수 입력 사항입니다.")
        String title,

        @NotNull(message = "내용은 필수 입력 사항입니다.")
        String content,

        @Min(value = 0, message = "입찰가는 0원 이상이어야 합니다.")
        int price,

        @Min(value = 0, message = "추가 단가는 0원 이상이어야 합니다.")
        int extraPrice,


        LocalDate endDate
) {
}
