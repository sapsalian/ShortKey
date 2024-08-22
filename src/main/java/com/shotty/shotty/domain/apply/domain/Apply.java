package com.shotty.shotty.domain.apply.domain;

import com.shotty.shotty.domain.apply.dto.ApplyRequestDto;
import com.shotty.shotty.domain.bid.domain.Bid;
import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "influencer_id")
    private Influencer influencer;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(mappedBy = "apply",cascade = CascadeType.REMOVE)
    private Bid bid;

    public Apply(String title, String content, String videoLink, Influencer influencer, Post post) {
        this.title = title;
        this.content = content;
        this.videoLink = videoLink;
        this.influencer = influencer;
        this.post = post;
    }

    public static Apply from(ApplyRequestDto applyRequestDto,Influencer influencer,Post post) {
        return new Apply(
                applyRequestDto.getTitle(),
                applyRequestDto.getContent(),
                applyRequestDto.getVideoLink(),
                influencer,
                post
        );
    }
}
