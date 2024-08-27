package com.shotty.shotty.domain.apply.dto;

public record ApplyQueryDto(
        boolean bidded,
        boolean uploaded,
        boolean accepted
) {
}
