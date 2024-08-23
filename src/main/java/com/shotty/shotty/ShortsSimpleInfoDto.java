package com.shotty.shotty;

import jdk.dynalink.beans.StaticClass;
import lombok.Data;

import java.util.List;

@Data
public class ShortsSimpleInfoDto {
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
        private String channelId;
        private String title;
        private String description;
        private String channelTitle;
    }
    @Data
    static class StatisticsSimple {
        private String viewCount;
        private String likeCount;
        private String favoriteCount;
        private String commentCount;
    }
}



