package com.shotty.shotty.youtube.dto.channel;

import lombok.Data;

@Data
public class YouTubeChannelItem {
    private String id;
    private ChannelSimpleSnippet snippet;
    private ChannelSimpleStatistics statistics;
}
