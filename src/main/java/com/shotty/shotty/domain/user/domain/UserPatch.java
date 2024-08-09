package com.shotty.shotty.domain.user.domain;

import com.shotty.shotty.domain.user.dto.UserPatchRequestDto;
import com.shotty.shotty.domain.user.dto.UserPutRequestDto;
import com.shotty.shotty.global.util.Hasher;

public record UserPatch(
    String userId,

    String password,

    String name,

    Boolean gender,

    String email
) {
    public static UserPatch from(UserPatchRequestDto userPatchRequestDto) {
        String originalPassword = userPatchRequestDto.userPassword();

        String encryptedPassword = null;
        if (originalPassword != null) {
            encryptedPassword = Hasher.hashPassword(originalPassword);
        }

        return new UserPatch(
                userPatchRequestDto.userId(),
                encryptedPassword,
                userPatchRequestDto.userName(),
                userPatchRequestDto.userGender(),
                userPatchRequestDto.userEmail()
        );
    }

    public static UserPatch from(UserPutRequestDto userPutRequestDto) {
        String originalPassword = userPutRequestDto.userPassword();

        String encryptedPassword = null;
        if (originalPassword != null) {
            encryptedPassword = Hasher.hashPassword(originalPassword);
        }

        return new UserPatch(
                userPutRequestDto.userId(),
                encryptedPassword,
                userPutRequestDto.userName(),
                userPutRequestDto.userGender(),
                userPutRequestDto.userEmail()
        );
    }
}
