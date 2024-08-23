package com.shotty.shotty;

import lombok.Data;

import java.util.List;

@Data
public class ChannelSimpleInfoDto {
    private List<YouTubeVideoItem> items;

    @Data
    static class YouTubeVideoItem {
        private String id;
        private SnippetSimple snippet;
        private StatisticsSimple statistics;
    }
    @Data
    static class SnippetSimple {
        private String publishedAt;
        private String title;
        private String description;
    }
    @Data
    static class StatisticsSimple {
        private String viewCount;
        private String subscriberCount;
        private String videoCount;
    }
}
