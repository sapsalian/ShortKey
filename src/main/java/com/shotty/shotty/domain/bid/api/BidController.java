package com.shotty.shotty.domain.bid.api;

import com.shotty.shotty.domain.bid.application.BidService;
import com.shotty.shotty.domain.bid.dto.BidRequestDto;
import com.shotty.shotty.domain.bid.dto.BidResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @PostMapping("applies/{applyId}/bids")
    public ResponseEntity<ResponseDto<BidResponseDto>> doBid(
            @Parameter(hidden = true)
            @TokenId
            Long userId,

            @PathVariable
            Long applyId
    ) {
        BidRequestDto bidRequestDto = new BidRequestDto(applyId, userId);
        BidResponseDto bidResponseDto = bidService.create(bidRequestDto);

        ResponseDto<BidResponseDto> responseDto = new ResponseDto<>(
                2011,
                "입찰 완료",
                bidResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }
}
