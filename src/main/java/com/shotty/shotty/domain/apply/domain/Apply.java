package com.shotty.shotty.domain.apply.domain;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "applies")
public class Apply {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private String videoLink;

    @CreatedDate
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "influencer_id")
    private Influencer influencer;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Apply(String title, String content, String videoLink, Influencer influencer, Post post) {
        this.title = title;
        this.content = content;
        this.videoLink = videoLink;
        this.influencer = influencer;
        this.post = post;
    }
}
