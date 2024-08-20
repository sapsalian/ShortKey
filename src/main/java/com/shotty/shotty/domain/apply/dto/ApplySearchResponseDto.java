package com.shotty.shotty.domain.apply.dto;

import com.shotty.shotty.domain.apply.domain.Apply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplySearchResponseDto {
    @Schema(description = "지원 식별 Id",example = "1L")
    private Long apply_id;
    @Schema(description = "지원 폼 제목",example = "구름빵 홍보 영상 지원합니다")
    private String title;

    @Schema(description = "공고 식별 Id",example = "1L")
    private Long post_id;

    @Schema(description = "등록한 공고의 title",example = "구름빵 홍보 영상")
    private String postTitle;

    @Schema(description = "지원의 입찰 여부",example = "true")
    private boolean bid;

    public static ApplySearchResponseDto from(Apply apply) {
        return new ApplySearchResponseDto(
                apply.getId(),
                apply.getTitle(),
                apply.getPost().getId(),
                apply.getPost().getTitle(),
                apply.isBidded()
        );
    }
}
