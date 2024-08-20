package com.shotty.shotty.domain.influencer.dto;

import com.shotty.shotty.domain.influencer.enums.Niche;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;


@Data
@AllArgsConstructor
public class InfluencerSearchInfo {
    private String userName;
    private Niche[] niche;
//    private Map

}
