package com.shotty.shotty.domain.bid.dto;

public record ShortsIdUploadDto(
        Long requesterId,
        Long applyId,
        String shortsId
) {
    public static ShortsIdUploadDto of(Long bidId, Long userId, ShortsIdRequestDto shortsIdRequestDto) {
        return new ShortsIdUploadDto(
                userId,
                bidId,
                shortsIdRequestDto.shortsId()
        );
    }
}
