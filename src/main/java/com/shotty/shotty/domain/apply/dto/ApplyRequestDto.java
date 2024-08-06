package com.shotty.shotty.domain.apply.dto;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class ApplyRequestDto {
    @Schema(description = "지원 폼 제목",example = "구름빵 홍보 영상 지원합니다")
    private String title;
    @Schema(description = "지원 폼 내용",example = "구름빵 홍보 영상 지원 내용")
    private String content;
    @Schema(description = "지원 폼 영상 링크",example = "exampleLink@@")
    private String videoLink;

}
