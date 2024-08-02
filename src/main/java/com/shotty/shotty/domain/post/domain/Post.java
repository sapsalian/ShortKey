package com.shotty.shotty.domain.post.domain;

import com.shotty.shotty.domain.post.dto.ImgContainedPostDto;
import com.shotty.shotty.domain.post.dto.PostRequestDto;
import com.shotty.shotty.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    private int price;

    private int extra_price;

    private String image;

    private LocalDateTime endDate;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Post(String title, String content, int price, int extra_price, String image, LocalDateTime endDate, User author) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.extra_price = extra_price;
        this.image = image;
        this.endDate = endDate;
        this.active = true;
        this.author = author;
    }

    public static Post of(ImgContainedPostDto imgContainedPoastDto, User author) {
        return new Post(
                imgContainedPoastDto.title(),
                imgContainedPoastDto.content(),
                imgContainedPoastDto.price(),
                imgContainedPoastDto.extraPrice(),
                imgContainedPoastDto.imageUrl(),
                imgContainedPoastDto.endDate(),
                author
        );
    }
}
