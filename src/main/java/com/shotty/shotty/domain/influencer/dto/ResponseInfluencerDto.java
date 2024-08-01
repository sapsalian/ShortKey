package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.enums.Niche;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ResponseInfluencerDto {
    private String channelId;
    private Long subscribers;
    private boolean verified;
    private Niche niche;
    private String profile_image;
    private UserResponseDto userResponseDto;

    public static ResponseInfluencerDto convertToDto(Influencer influencer) {
        ResponseInfluencerDto dto = new ResponseInfluencerDto();
        dto.setChannelId(influencer.getChannelId());
        dto.setSubscribers(influencer.getSubscribers());
        dto.setVerified(influencer.isVerified());
        dto.setNiche(influencer.getNiche());
        dto.setProfile_image(influencer.getProfile_image());
        dto.setUserResponseDto(UserResponseDto.from(influencer.getUser()));
        return dto;
    }

}
