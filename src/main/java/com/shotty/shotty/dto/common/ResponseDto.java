package com.shotty.shotty.dto.common;

public record ResponseDto<T>(
        int code,
        String statusMsg,
        T data
) {
}
