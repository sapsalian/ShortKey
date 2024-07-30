package com.shotty.shotty.dto.common;

public record ResponseDto<T>(
        short code,
        String statusMsg,
        T data
) {
}
