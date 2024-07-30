package com.shotty.shotty.dto;

import com.shotty.shotty.Domain.User;

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
