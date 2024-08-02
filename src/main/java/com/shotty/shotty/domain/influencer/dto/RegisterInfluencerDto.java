package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.enums.Niche;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Schema(description = "인플루언서 등록 요청 형식")
public class RegisterInfluencerDto {
    private String userId;
    private String channelId;
    private Long subscribers;
    private Niche niche;
    private String profile_image;

}
