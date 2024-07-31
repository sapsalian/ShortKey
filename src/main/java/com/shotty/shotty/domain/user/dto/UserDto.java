package com.shotty.shotty.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
