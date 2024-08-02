package com.shotty.shotty.domain.influencer.api;

import com.shotty.shotty.domain.influencer.application.InfluencerService;
import com.shotty.shotty.domain.influencer.dto.RegisterInfluencerDto;
import com.shotty.shotty.domain.influencer.dto.ResponseInfluencerDto;
import com.shotty.shotty.domain.influencer.dto.SaveInfluencerDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InfluencerController {
    private final InfluencerService influencerService;

    @GetMapping("/influencers/{id}")
    public ResponseEntity<ResponseDto<ResponseInfluencerDto>> getInfluencer(@PathVariable("id") Long influencer_id) {
        ResponseInfluencerDto responseInfluencerDto = influencerService.findOne(influencer_id);

        ResponseDto<ResponseInfluencerDto> influence = new ResponseDto<>(
                2004,
                "인플루언서 개별 조회 성공",
                responseInfluencerDto
        );
        return ResponseEntity.ok(influence);
    }

    @GetMapping("/influencers")
    public ResponseEntity<ResponseDto<Page<ResponseInfluencerDto>>> getAllInfluencers(Pageable pageable) {
        Page<ResponseInfluencerDto> influencers = influencerService.findAllInfluencers(pageable);

        ResponseDto<Page<ResponseInfluencerDto>> responseDto = new ResponseDto<>(
                2003,
                "인플루언서 전체 조회 성공",
                influencers
        );
        return ResponseEntity.ok(responseDto);
    }
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
