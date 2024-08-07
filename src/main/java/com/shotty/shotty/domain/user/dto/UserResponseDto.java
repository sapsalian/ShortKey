package com.shotty.shotty.domain.user.dto;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저응답 DTO")
public record UserResponseDto(
        @Schema(description = "사용자를 식별하는 id 값", example = "1")
        Long id,
        @Schema(description = "사용자가 로그인할 때 사용하는 id", example = "myid12345")
        String userId,
        @Schema(description = "다른 사용자들에게 표시되는 이름", example = "myName")
        String userName,
        @Schema(description = "사용자의 성별", example = "true")
        Boolean userGender,
        @Schema(description = "사용자의 email", example = "test1234@test.com")
        String userEmail,
        @Schema(description = "사용자가 인플루언서로 등록되어 있으면 1, 아니면 0", example = "1")
        short userRole,
        @Schema(description = "사용자가 인플루언서로 등록되어 있으면 인플루언서 Id, 아니면 null", example = "3")
        Long influencerId
) {
    public static UserResponseDto from(User user) {
        Influencer influencer = user.getInfluencer();
        Long influencerId = influencer != null
                ? influencer.getId()
                : null;

        return new UserResponseDto(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.isGender(),
                user.getEmail(),
                user.getRole().getRoleNum(),
                influencerId
        );
    }
}
