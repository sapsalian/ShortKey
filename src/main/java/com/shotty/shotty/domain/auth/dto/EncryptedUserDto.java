package com.shotty.shotty.domain.auth.dto;

import com.shotty.shotty.domain.auth.enums.UserRoleEnum;
import com.shotty.shotty.global.util.Hasher;

public record EncryptedUserDto (
        String userId,

        String encryptedPassword,

        String userName,

        UserRoleEnum userRoleEnum
) {
    public static EncryptedUserDto from(ResisterRequestDto resisterRequestDto) {
        String plainPassword = resisterRequestDto.userPassword();
        String encryptedPassword = Hasher.hashPassword(plainPassword);

        short roleNum = resisterRequestDto.userRole();
        UserRoleEnum userRoleEnum = UserRoleEnum.getUserRoleByRoleNum(roleNum);

        return new EncryptedUserDto(
                resisterRequestDto.userId(),
                encryptedPassword,
                resisterRequestDto.userName(),
                userRoleEnum
        );
    }
}

