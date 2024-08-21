package com.shotty.shotty.domain.post.dto;


import java.time.LocalDate;

public record ImgContainedPostDto(
        String title,
        String content,
        int price,
        int extraPrice,
        String postImage,
        LocalDate endDate
) {
    public static ImgContainedPostDto of(PostRequestDto postRequestDto, String post_image) {
        return new ImgContainedPostDto(
                postRequestDto.title(),
                postRequestDto.content(),
                postRequestDto.price(),
                postRequestDto.extraPrice(),
                post_image,
                postRequestDto.endDate()
        );
    }
}
