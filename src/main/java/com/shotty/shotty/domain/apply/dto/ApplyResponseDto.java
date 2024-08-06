package com.shotty.shotty.domain.apply.dto;

import com.shotty.shotty.domain.apply.domain.Apply;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplyResponseDto {
    private Long id;

    private String title;

    private String content;

    private String videoLink;

    private String influencerChannelId;

    private String postTitle;

    public static ApplyResponseDto from(Apply apply) {
        return new ApplyResponseDto(
                apply.getId(),
                apply.getTitle(),
                apply.getContent(),
                apply.getVideoLink(),
                apply.getInfluencer().getChannelId(),
                apply.getPost().getTitle()
        );
    }
}
