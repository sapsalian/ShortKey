package com.shotty.shotty.domain.post.dto;


import java.time.LocalDate;

public record ImgContainedPostDto(
        String title,
        String content,
        int price,
        int extraPrice,
        String imageUrl,
        LocalDate endDate
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
