package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.enums.Niche;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveInfluencerDto {
    private String userId;
    private String channelId;
    private Long subscribers;
    private Niche niche;
    private String profile_image;

    public static SaveInfluencerDto from(RegisterInfluencerDto registerInfluencerDto){
        return new SaveInfluencerDto(
                registerInfluencerDto.getUserId(),
                registerInfluencerDto.getChannelId(),
                registerInfluencerDto.getSubscribers(),
                registerInfluencerDto.getNiche(),
                registerInfluencerDto.getProfile_image()
        );
    }
}
