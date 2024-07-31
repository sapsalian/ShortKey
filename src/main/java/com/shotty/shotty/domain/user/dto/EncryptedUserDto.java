package com.shotty.shotty.domain.user.dto;

import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import com.shotty.shotty.global.util.Hasher;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "암호화된 유저 DTO")
public record EncryptedUserDto (
        String userId,

        String encryptedPassword,

        String userName,

        Boolean userGender,

        String userEmail
) {
    public static EncryptedUserDto from(ResisterRequestDto resisterRequestDto) {
        String plainPassword = resisterRequestDto.userPassword();
        String encryptedPassword = Hasher.hashPassword(plainPassword);

        return new EncryptedUserDto(
                resisterRequestDto.userId(),
                encryptedPassword,
                resisterRequestDto.userName(),
                resisterRequestDto.userGender(),
                resisterRequestDto.userEmail()
        );
    }
}

