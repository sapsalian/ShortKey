package com.shotty.shotty.domain.user.domain;

import com.shotty.shotty.domain.user.dto.UserPatchRequestDto;

public record UserPatch(
    String userId,

    String password,

    String name,

    Boolean gender,

    String email
) {
    public static UserPatch from(UserPatchRequestDto userPatchRequestDto) {
        return new UserPatch(
                userPatchRequestDto.userId(),
                userPatchRequestDto.userPassword(),
                userPatchRequestDto.userName(),
                userPatchRequestDto.userGender(),
                userPatchRequestDto.userEmail()
        );
    }
}
