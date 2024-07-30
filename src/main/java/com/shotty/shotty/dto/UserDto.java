package com.shotty.shotty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class loginRequest{
        String userId;
        String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class loginResponse{

    }
}
