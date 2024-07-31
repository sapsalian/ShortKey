package com.shotty.shotty.domain.post.domain;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content, int price, int extra_price, User user) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.extra_price = extra_price;
        this.user = user;
    }
}
