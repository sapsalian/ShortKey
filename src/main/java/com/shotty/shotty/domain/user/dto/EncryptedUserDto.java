package com.shotty.shotty.domain.user.dto;

import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import com.shotty.shotty.global.util.Hasher;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "암호화된 유저 DTO")
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

