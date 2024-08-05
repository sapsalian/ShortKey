package com.shotty.shotty.domain.post.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record PostPatchDto(
        String title,

        String content,

        @Min(value = 0, message = "입찰가는 0원 이상이어야 합니다.")
        @Nullable
        Integer price,

        @Min(value = 0, message = "추가 단가는 0원 이상이어야 합니다.")
        @Nullable
        Integer extraPrice,


        LocalDate endDate
) {
}
