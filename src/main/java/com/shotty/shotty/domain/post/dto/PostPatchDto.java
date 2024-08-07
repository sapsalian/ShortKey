package com.shotty.shotty.domain.post.dto;

import com.shotty.shotty.domain.post.custom_annotation.annotation.AfterOrEqualToday;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record PostPatchDto(
        @Schema(description = "공고 제목",example = "구름빵 홍보 영상")
        String title,

        @Schema(description = "공고 내용",example = "맛있는 구름빵을 홍보해주세요.")
        String content,

        @Schema(description = "공고 입찰가",example = "500000")
        @Min(value = 0, message = "입찰가는 0원 이상이어야 합니다.")
        @Nullable
        Integer price,

        @Schema(description = "조회수 당 추가 단가",example = "300")
        @Min(value = 0, message = "추가 단가는 0원 이상이어야 합니다.")
        @Nullable
        Integer extraPrice,

        @Schema(description = "마감 일자",example = "2024-08-26")
        @AfterOrEqualToday(message = "마감일은 현재 날짜 이후여야 합니다.")
        LocalDate endDate
) {
}
