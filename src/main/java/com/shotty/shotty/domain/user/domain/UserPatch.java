package com.shotty.shotty.domain.user.domain;

import com.shotty.shotty.domain.user.dto.UserPatchRequestDto;
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
        String encryptedPassword = Hasher.hashPassword(originalPassword);

        return new UserPatch(
                userPatchRequestDto.userId(),
                encryptedPassword,
                userPatchRequestDto.userName(),
                userPatchRequestDto.userGender(),
                userPatchRequestDto.userEmail()
        );
    }
}
