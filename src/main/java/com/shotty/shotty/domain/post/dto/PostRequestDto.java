package com.shotty.shotty.domain.post.dto;

import com.shotty.shotty.domain.post.custom_annotation.annotation.AfterOrEqualToday;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record PostRequestDto(
        @Schema(description = "공고 제목",example = "구름빵 홍보 영상")
        @NotNull(message = "공고 제목은 필수 입력 사항입니다.")
        String title,

        @Schema(description = "공고 내용",example = "맛있는 구름빵을 홍보해주세요.")
        @NotNull(message = "내용은 필수 입력 사항입니다.")
        String content,

        @Schema(description = "공고 입찰가",example = "500000")
        @Min(value = 0, message = "입찰가는 0원 이상이어야 합니다.")
        @NotNull(message = "입찰가는 필수 입력 사항입니다.")
        Integer price,

        @Schema(description = "조회수 당 추가 단가",example = "300")
        @Min(value = 0, message = "추가 단가는 0원 이상이어야 합니다.")
        @NotNull(message = "추가 단가는 필수 입력 사항입니다.")
        Integer extraPrice,

        @Schema(description = "마감 일자",example = "2024-08-26")
        @AfterOrEqualToday(message = "마감일은 현재 날짜 이후여야 합니다.")
        @NotNull(message = "마감일은 필수 입력 사항입니다.")
        LocalDate endDate,

        @Schema(description = "공고 이미지 파일",example = "hannie.png")
        @Nullable
        MultipartFile post_image
) {
}
