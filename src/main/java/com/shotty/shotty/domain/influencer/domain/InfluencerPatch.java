package com.shotty.shotty.domain.influencer.domain;

import com.shotty.shotty.domain.influencer.dto.InfluencerUpdateRequestDto;
import com.shotty.shotty.domain.influencer.enums.Niche;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfluencerPatch {
    private String channelId;
    private Long subscribers;
    private Niche niche;
    private String profile_image;

    public static InfluencerPatch from(InfluencerUpdateRequestDto requestDto) {
        return new InfluencerPatch(
                requestDto.getChannelId(),
                requestDto.getSubscribers(),
                requestDto.getNiche(),
                requestDto.getProfile_image()
        );
    }
}
