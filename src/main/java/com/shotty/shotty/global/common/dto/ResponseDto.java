package com.shotty.shotty.global.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ResponseDto<T>(
        @Schema(description = "커스텀 상태 코드",example = "2001")
        int code,

        @Schema(description = "상태 메세지",example = "예시 상태메세지입니다.")
        String statusMsg,

        T data
) {
}
