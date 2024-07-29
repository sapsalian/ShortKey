package com.shotty.shotty.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record ResisterRequestDto(
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{3,15}$", message = "아이디는 영문 대소문자와 숫자만 허용되며, 3~15자 사이여야 합니다.")
        String userId,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}$", message = "비밀번호는 영문 대문자, 소문자, 숫자가 각각 하나 이상 포함되어야 하며, 8~15자 사이여야 합니다.")
        String userPassword,

        @Pattern(regexp = "^[가-힣]{2,20}$", message = "이름은 2~20자 사이어야 합니다.")
        String userName,

        @Min(value=0, message = "역할은 0(advertiser) 또는 1(influencer) 이어야 합니다.")
        @Max(value=1, message = "역할은 0(advertiser) 또는 1(influencer) 이어야 합니다.")
        short userRole
) {
}
