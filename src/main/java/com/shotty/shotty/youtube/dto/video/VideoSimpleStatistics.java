package com.shotty.shotty.youtube.dto.video;

import lombok.Data;

@Data
public class VideoSimpleStatistics {
    private String viewCount;
    private String likeCount;
    private String favoriteCount;
    private String commentCount;
}
