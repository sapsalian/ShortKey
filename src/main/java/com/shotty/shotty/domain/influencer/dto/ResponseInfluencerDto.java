package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.enums.Niche;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseInfluencerDto {
    private Long id;
    private String channelId;
    private Long subscribers;
    private boolean verified;
    private Niche niche;
    private String profile_image;
    private UserResponseDto userInfo;

    @Schema(description = "응답을 위한 인플루언서 Dto ")
    public static ResponseInfluencerDto from(Influencer influencer) {
        return new ResponseInfluencerDto(
                influencer.getId(),
                influencer.getChannelId(),
                influencer.getSubscribers(),
                influencer.isVerified(),
                influencer.getNiche(),
                influencer.getProfile_image(),
                UserResponseDto.from(influencer.getUser())

        );
    }

}
