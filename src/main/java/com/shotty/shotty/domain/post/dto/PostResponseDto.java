package com.shotty.shotty.domain.post.dto;

import com.shotty.shotty.domain.post.domain.Post;

import java.time.LocalDate;

public record PostResponseDto(
    Long id,
    String title,
    String content,
    LocalDate createdAt,
    int price,
    int extraPrice,
    String image,
    LocalDate endDate,
    Long authorId
) {
    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getPrice(),
                post.getExtra_price(),
                post.getImage(),
                post.getEndDate(),
                post.getAuthor().getId()
        );
    }
}
