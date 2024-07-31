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
        String userId;
        String userPassword;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class loginResponse{

    }
}
