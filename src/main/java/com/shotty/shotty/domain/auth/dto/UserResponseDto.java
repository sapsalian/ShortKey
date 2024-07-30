package com.shotty.shotty.domain.auth.dto;

import com.shotty.shotty.domain.auth.domain.User;

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
