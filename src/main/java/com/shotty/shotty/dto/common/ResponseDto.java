package com.shotty.shotty.dto.common;

public record ResponseDto(
        short code,
        String statusMsg,
        Object data
) {
}
