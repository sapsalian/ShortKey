package com.shotty.shotty.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "회원가입요청 DTO")
public record ResisterRequestDto(
        @NotNull(message = "Id는 필수 입력해야 합니다.")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{3,15}$", message = "아이디는 영문 대소문자와 숫자만 허용되며, 3~15자 사이여야 합니다.")
        String userId,

        @NotNull(message = "password는 필수 입력해야 합니다.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}$", message = "비밀번호는 영문 대문자, 소문자, 숫자가 각각 하나 이상 포함되어야 하며, 8~15자 사이여야 합니다.")
        String userPassword,

        @NotNull(message = "이름은 필수 입력해야 합니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "이름은 2~20자 사이어야 합니다.")
        String userName,

        Boolean userGender,

        @NotNull(message = "email은 필수 입력해야 합니다.")
        @Email(message = "올바른 email 형식이 아닙니다.")
        String userEmail
) {
}
