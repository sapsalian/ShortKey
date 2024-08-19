package com.shotty.shotty.domain.post.domain;

import com.shotty.shotty.domain.post.dto.ImgContainedPostDto;
import com.shotty.shotty.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
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

    @CreationTimestamp
    private LocalDateTime createdAt;

    private int price;

    private int extraPrice;

    private String post_image;

    private LocalDate endDate;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Post(String title, String content, int price, int extraPrice, String post_image, LocalDate endDate, User author) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.extraPrice = extraPrice;
        this.post_image = post_image;
        this.endDate = endDate;
        this.active = true;
        this.author = author;
    }

    public void deactivate() {
        this.active = false;
    }

    public static Post of(ImgContainedPostDto imgContainedPoastDto, User author) {
        return new Post(
                imgContainedPoastDto.title(),
                imgContainedPoastDto.content(),
                imgContainedPoastDto.price(),
                imgContainedPoastDto.extraPrice(),
                imgContainedPoastDto.post_image(),
                imgContainedPoastDto.endDate(),
                author
        );
    }
}
