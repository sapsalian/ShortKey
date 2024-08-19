package com.shotty.shotty.domain.apply.dto;

import com.shotty.shotty.domain.apply.domain.Apply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplyResponseDto {
    @Schema(description = "지원 식별 Id",example = "1L")
    private Long apply_id;
    @Schema(description = "지원 폼 제목",example = "구름빵 홍보 영상 지원합니다")
    private String title;

    @Schema(description = "지원 폼 내용",example = "구름빵 홍보 영상 지원 내용")
    private String content;

    @Schema(description = "지원 폼 링크",example = "exampleLink@@")
    private String videoLink;

    @Schema(description = "등록한 인플러언서의 채널 Id",example = "channelid")
    private String influencerChannelId;

    @Schema(description = "공고 식별 Id",example = "1L")
    private Long post_id;
    @Schema(description = "등록한 공고의 제목",example = "구름빵 홍보 영상")
    private String postTitle;

    @Schema(description = "등록한 공고의 내용",example = "구름빵 홍보 영상 지원 내용")
    private String postContent;

    public static ApplyResponseDto from(Apply apply) {
        return new ApplyResponseDto(
                apply.getId(),
                apply.getTitle(),
                apply.getContent(),
                apply.getVideoLink(),
                apply.getInfluencer().getChannelId(),
                apply.getPost().getId(),
                apply.getPost().getTitle(),
                apply.getPost().getContent()
        );
    }
}
