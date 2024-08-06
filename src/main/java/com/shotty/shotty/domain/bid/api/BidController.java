package com.shotty.shotty.domain.bid.api;

import com.shotty.shotty.domain.bid.application.BidService;
import com.shotty.shotty.domain.bid.dto.BidRequestDto;
import com.shotty.shotty.domain.bid.dto.BidResponseDto;
import com.shotty.shotty.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @PostMapping("/api/bids")
    public ResponseEntity<ResponseDto<BidResponseDto>> doBid(@RequestBody BidRequestDto requestDto) {
        BidResponseDto bidResponseDto = bidService.create(requestDto);

        ResponseDto<BidResponseDto> responseDto = new ResponseDto<>(
                2011,
                "입찰 완료",
                bidResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }
}
