package com.shotty.shotty.youtube.dto.channel;

import lombok.Data;

import java.util.List;

@Data
public class ChannelSimpleInfoDto {
    private List<YouTubeChannelItem> items;

}
