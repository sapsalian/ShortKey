package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.enums.Niche;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RegisterInfluencerDto {
    private String userId;
    private String channelId;
    private Long subscribers;
    private Niche niche;
    private String profile_image;

}
