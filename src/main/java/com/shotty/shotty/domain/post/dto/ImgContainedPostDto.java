package com.shotty.shotty.domain.post.dto;


import java.time.LocalDateTime;

public record ImgContainedPostDto(
        String title,
        String content,
        int price,
        int extraPrice,
        String imageUrl,
        LocalDateTime endDate
) {
    public static ImgContainedPostDto of(PostRequestDto postRequestDto, String imageUrl) {
        return new ImgContainedPostDto(
                postRequestDto.title(),
                postRequestDto.content(),
                postRequestDto.price(),
                postRequestDto.extraPrice(),
                imageUrl,
                postRequestDto.endDate()
        );
    }
}
