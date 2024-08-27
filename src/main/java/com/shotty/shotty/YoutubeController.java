package com.shotty.shotty;

import com.shotty.shotty.youtube.dto.channel.ChannelSimpleInfoDto;
import com.shotty.shotty.youtube.dto.video.ShortsSimpleInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/youtube")
public class YoutubeController {
    private final YoutubeService youtubeService;

    @GetMapping("/shorts")
    public ResponseEntity<ShortsSimpleInfoDto> getSearchVideo(@RequestParam String shorts_id) {
        return youtubeService.searchVideo(shorts_id);
    }

    @GetMapping("/channels")
    public ResponseEntity<ChannelSimpleInfoDto> getSearchChannel(@RequestParam String channel_id) {
        return youtubeService.searchChannel(channel_id);
    }
}
