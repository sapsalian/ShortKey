package com.shotty.shotty.domain.bid.dto;

public record ShortsIdUploadDto(
        String shortsId,
        Long bidId
) {
    public static ShortsIdUploadDto of(Long bidId, ShortsIdRequestDto shortsIdRequestDto) {
        return new ShortsIdUploadDto(
                shortsIdRequestDto.shortsId(),
                bidId
        );
    }
}
