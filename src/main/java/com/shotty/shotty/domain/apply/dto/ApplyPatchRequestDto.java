package com.shotty.shotty.domain.apply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplyPatchRequestDto {
    @Schema(description = "지원 폼 제목",example = "구름빵 홍보 영상 지원합니다")
    @NotNull
    private String title;

    @Schema(description = "지원 폼 내용",example = "구름빵 홍보 영상 지원 내용")
    @NotNull
    private String content;

    @Schema(description = "지원 폼 영상 링크",example = "exampleLink@@")
    @NotNull
    private String videoLink;
}
