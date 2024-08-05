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
    @Schema(description = "유튜브 채널 ID",example = "channelid")
    private String channelId;
    @Nullable
    @Schema(description = "유튜브 구독자 수",example = "50000")
    private Long subscribers;
    @Nullable
    @Schema(description = "관심분야",example = "FISHING")
    private Niche niche;
    @Nullable
    @Schema(description = "프로필 이미지 url",example = "https//asdasdad...")
    private String profile_image;
}
