package com.shotty.shotty.domain.post.dto;

import com.shotty.shotty.domain.post.domain.Post;

import java.time.LocalDateTime;

public record PostResponseDto(
    Long id,
    String title,
    String content,
    LocalDateTime createdAt,
    int price,
    int extraPrice,
    String image,
    LocalDateTime endDate,
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
