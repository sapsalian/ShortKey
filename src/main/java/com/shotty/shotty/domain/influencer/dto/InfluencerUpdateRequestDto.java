package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.enums.Niche;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "인플루언서 수정 요청 형식")
public class InfluencerUpdateRequestDto {
    @NotNull
    @Schema(description = "유튜브 채널 ID",example = "channelid")
    private String channelId;
    @NotNull
    @Schema(description = "유튜브 구독자 수",example = "50000")
    private Long subscribers;
    @NotNull
    @Schema(description = "관심분야",example = "FISHING")
    private Niche niche;
    @NotNull
    @Schema(description = "프로필 이미지 url",example = "hanni.png")
    private MultipartFile profile_image;
}
