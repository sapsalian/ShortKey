package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.enums.Niche;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseInfluencerDto {
    @Schema(description = "인플루언서 식별 id",example = "1")
    private Long id;
    @Schema(description = "유튜브 채널 ID",example = "channelid")
    private String channelId;
    @Schema(description = "유튜브 구독자 수",example = "50000")
    private Long subscribers;
    @Schema(description = "검증여부",example = "true")
    private boolean verified;
    @Schema(description = "관심분야",example = "FISHING")
    private Niche niche;
    @Schema(description = "프로필 이미지 url",example = "https//asdasdad...")
    private String profile_image;
    @Schema(description = "해당 인플루언서의 유저 정보")
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
