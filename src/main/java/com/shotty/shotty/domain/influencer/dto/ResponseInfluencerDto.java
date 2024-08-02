package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.enums.Niche;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ResponseInfluencerDto {
    private Long id;
    private String channelId;
    private Long subscribers;
    private boolean verified;
    private Niche niche;
    private String profile_image;
    private UserResponseDto userInfo;

    @Schema(description = "응답을 위한 인플루언서 Dto ")
    public static ResponseInfluencerDto convertToDto(Influencer influencer) {
        ResponseInfluencerDto dto = new ResponseInfluencerDto();
        dto.setId(influencer.getId());
        dto.setChannelId(influencer.getChannelId());
        dto.setSubscribers(influencer.getSubscribers());
        dto.setVerified(influencer.isVerified());
        dto.setNiche(influencer.getNiche());
        dto.setProfile_image(influencer.getProfile_image());
        dto.setUserInfo(UserResponseDto.from(influencer.getUser()));
        return dto;
    }

}
