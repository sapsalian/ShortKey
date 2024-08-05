package com.shotty.shotty.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserPatchRequestDto(
        @Nullable
        @Schema(description = "영문 대소문자와 숫자만 허용되며, 3~15자 사이",example = "myid12345")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{3,15}$", message = "아이디는 영문 대소문자와 숫자만 허용되며, 3~15자 사이여야 합니다.")
        String userId,

        @Nullable
        @Schema(description = "영문 대문자, 소문자, 숫자가 각각 하나 이상 포함되어야 하며, 8~15자 사이",example = "Password123")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}$", message = "비밀번호는 영문 대문자, 소문자, 숫자가 각각 하나 이상 포함되어야 하며, 8~15자 사이여야 합니다.")
        String userPassword,

        @Nullable
        @Schema(description = "2~20자 사이",example = "tester")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "이름은 2~20자 사이어야 합니다.")
        String userName,

        @Nullable
        @Schema(description = "성별) 남자:true,여자:false",example = "true")
        Boolean userGender,

        @Nullable
        @Schema(description = "사용자 이메일",example = "test1234@test.com")
        @Email(message = "올바른 email 형식이 아닙니다.")
        String userEmail
) {
}
