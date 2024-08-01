package com.shotty.shotty.domain.influencer.api;

import com.shotty.shotty.domain.influencer.application.InfluencerService;
import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.dto.RegisterInfluencerDto;
import com.shotty.shotty.domain.influencer.dto.SaveInfluencerDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InfluencerRegisterController {
    private final InfluencerService influencerService;
    @PostMapping("/influencers")
    public ResponseEntity<ResponseDto<Null>> registerInfluencer(@TokenId Long user_id, @RequestBody RegisterInfluencerDto registerInfluencerDto) {
        SaveInfluencerDto saveInfluencerDto = SaveInfluencerDto.from(registerInfluencerDto);
        influencerService.register(user_id, saveInfluencerDto);
        ResponseDto<Null> responseDto = new ResponseDto<>(
                2011,
                "인플루언서 등록 성공",
                null
        );
        return ResponseEntity.ok(responseDto);
    }

}
