package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.enums.Niche;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
@Schema(description = "인플루언서 수정 요청 형식")
public class InfluencerPatchRequestDto {
    @Nullable
    private String channelId;
    @Nullable
    private Long subscribers;
    @Nullable
    private Niche niche;
    @Nullable
    private String profile_image;
}
