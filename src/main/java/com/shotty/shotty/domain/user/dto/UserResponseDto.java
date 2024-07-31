package com.shotty.shotty.domain.user.dto;

import com.shotty.shotty.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저응답 DTO")
public record UserResponseDto(
        Long id,
        String userId,
        String userName,
        Short userRole
) {
    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getRole().getRoleNum()
        );
    }
}
