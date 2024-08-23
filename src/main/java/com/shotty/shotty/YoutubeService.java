package com.shotty.shotty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class YoutubeService {
    private static final String VIDEO_API_URL = "https://youtube.googleapis.com/youtube/v3/videos";
    private static final String CHANNEL_API_URL = "https://www.googleapis.com/youtube/v3/channels";

    @Value("${youtube.api.key}")
    private String API_KEY;

    public ResponseEntity<ShortsSimpleInfoDto> searchVideo(String shorts_id){
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(VIDEO_API_URL)
                .queryParam("part", "snippet,statistics")
                .queryParam("id", shorts_id)
                .queryParam("key", API_KEY);

        String url = uriBuilder.toUriString();

        ResponseEntity<ShortsSimpleInfoDto> response = restTemplate.getForEntity(url, ShortsSimpleInfoDto.class);

        return response;
    }

    public ResponseEntity<ChannelSimpleInfoDto> searchChannel(String channel_id){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(CHANNEL_API_URL)
                .queryParam("part", "snippet,statistics")
                .queryParam("id", channel_id)
                .queryParam("key", API_KEY);

        String url = uriBuilder.toUriString();
        ResponseEntity<ChannelSimpleInfoDto> response = restTemplate.getForEntity(url, ChannelSimpleInfoDto.class);
        return response;
    }
}
