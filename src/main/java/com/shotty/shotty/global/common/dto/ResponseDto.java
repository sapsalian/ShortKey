package com.shotty.shotty.global.common.dto;

public record ResponseDto<T>(
        int code,
        String statusMsg,
        T data
) {
}
