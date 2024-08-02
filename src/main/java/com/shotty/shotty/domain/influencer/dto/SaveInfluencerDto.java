package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.enums.Niche;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "인플루언서 등록 서비스단에서 저장을 위한 dto")
public class SaveInfluencerDto {
    private String channelId;
    private Long subscribers;
    private Niche niche;
    private String profile_image;

    public static SaveInfluencerDto from(RegisterInfluencerDto registerInfluencerDto){
        return new SaveInfluencerDto(
                registerInfluencerDto.getChannelId(),
                registerInfluencerDto.getSubscribers(),
                registerInfluencerDto.getNiche(),
                registerInfluencerDto.getProfile_image()
        );
    }
}
