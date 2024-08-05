package com.shotty.shotty.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "유저로그인 DTO")
public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class loginRequest{
        @Schema(description = "영문 대소문자와 숫자만 허용되며, 3~15자 사이", example = "myid12345")
        String userId;
        @Schema(description = "영문 대문자, 소문자, 숫자가 각각 하나 이상 포함되어야 하며, 8~15자 사이", example = "Password123")
        String userPassword;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class loginResponse{

    }
}
