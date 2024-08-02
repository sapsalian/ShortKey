package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.enums.Niche;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "인플루언서 등록 요청 형식")
public class RegisterInfluencerDto {
    @NotNull(message = "채널ID는 필수 입력해야 합니다")
    private String channelId;
    @Nullable
    private Long subscribers;
    @Nullable
    private Niche niche;
    @Nullable
    private String profile_image;

}
