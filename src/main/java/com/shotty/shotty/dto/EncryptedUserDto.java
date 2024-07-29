package com.shotty.shotty.dto;

import com.shotty.shotty.Domain.UserRole;
import com.shotty.shotty.util.Hasher;

public record EncryptedUserDto (
        String userId,

        String encryptedPassword,

        String userName,

        UserRole userRole
) {
    public static EncryptedUserDto from(ResisterRequestDto resisterRequestDto) {
        String plainPassword = resisterRequestDto.userPassword();
        String encryptedPassword = Hasher.hashPassword(plainPassword);

        short roleNum = resisterRequestDto.userRole();
        UserRole userRole = UserRole.getUserRoleByRoleNum(roleNum);

        return new EncryptedUserDto(
                resisterRequestDto.userId(),
                encryptedPassword,
                resisterRequestDto.userName(),
                userRole
        );
    }
}

