package com.shotty.shotty.domain.post.dto;

import com.shotty.shotty.domain.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record PostResponseDto(
    Long id,

    @Schema(description = "공고 제목",example = "구름빵 홍보 영상")
    String title,

    @Schema(description = "공고 내용",example = "맛있는 구름빵을 홍보해주세요.")
    String content,

    @Schema(description = "공고 생성 일자",example = "2024-08-04")
    LocalDate createdAt,

    @Schema(description = "공고 입찰가",example = "500000")
    int price,

    @Schema(description = "조회수 당 추가 단가",example = "300")
    int extraPrice,

    @Schema(description = "첨부 이미지 URL",example = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfH57DsS4MAwbTEesCC-jMQ5sDfqrNcWm71g&s")
    String image,

    @Schema(description = "마감 일자",example = "2024-08-26")
    LocalDate endDate,

    @Schema(description = "작성자의 Id(식별키 값)",example = "1")
    Long authorId,

    @Schema(description = "작성자의 이름", example = "김수한무")
    String authorName
) {
    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt().toLocalDate(),
                post.getPrice(),
                post.getExtraPrice(),
                post.getPost_image(),
                post.getEndDate(),
                post.getAuthor().getId(),
                post.getAuthor().getName()
        );
    }
}
