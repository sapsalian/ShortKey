package com.shotty.shotty.youtube.dto.video;

import lombok.Data;

@Data
public class YouTubeVideoItem {
    private String id;
    private VideoSimpeSnippet snippet;
    private VideoSimpleStatistics statistics;
}
