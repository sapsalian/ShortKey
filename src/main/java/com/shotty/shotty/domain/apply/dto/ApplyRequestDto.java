package com.shotty.shotty.domain.apply.dto;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.post.domain.Post;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class ApplyRequestDto {
    private String title;

    private String content;

    private String videoLink;

}
