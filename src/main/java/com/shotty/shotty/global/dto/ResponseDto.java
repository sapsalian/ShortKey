package com.shotty.shotty.global.dto;

public record ResponseDto<T>(
        int code,
        String statusMsg,
        T data
) {
}
