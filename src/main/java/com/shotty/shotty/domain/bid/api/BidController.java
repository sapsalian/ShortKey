package com.shotty.shotty.domain.bid.api;

import com.shotty.shotty.domain.bid.application.BidService;
import com.shotty.shotty.domain.bid.dto.BidRequestDto;
import com.shotty.shotty.domain.bid.dto.BidResponseDto;
import com.shotty.shotty.domain.bid.dto.ShortsIdRequestDto;
import com.shotty.shotty.domain.bid.dto.ShortsIdUploadDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Null;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @PostMapping("/api/applies/{applyId}/bids")
    public ResponseEntity<ResponseDto<Null>> doBid(
            @Parameter(hidden = true)
            @TokenId
            Long userId,

            @PathVariable
            Long applyId
    ) {
        BidRequestDto bidRequestDto = new BidRequestDto(applyId, userId);
        bidService.create(bidRequestDto);

        ResponseDto<Null> responseDto = new ResponseDto<>(
                2011,
                "입찰 완료",
                null
        );

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/api/bids/{bidId}/shorts")
    public ResponseEntity<ResponseDto<Null>> uploadShortsId(
            @TokenId
            @Parameter(hidden = true)
            Long userId,

            @PathVariable
            Long bidId,

            @RequestBody
            ShortsIdRequestDto shortsIdRequestDto
    ) {
        ShortsIdUploadDto shortsIdUploadDto = ShortsIdUploadDto.of(bidId, userId, shortsIdRequestDto);

        bidService.updateShortsId(shortsIdUploadDto);

        ResponseDto<Null> responseDto = new ResponseDto<>(
                2007,
                "쇼츠 ID 등록 성공",
                null
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/api/bids/{bidId}/accept")
    public ResponseEntity<ResponseDto<Null>> acceptBid(
            @PathParam("bidId")
            Long bidId,

            @TokenId
            Long accepterId
    ) {
        bidService.acceptBid(accepterId, bidId);

        ResponseDto<Null> responseDto = new ResponseDto<>(
                2008,
                "광고주 승인 완료",
                null
        );

        return ResponseEntity.ok(responseDto);
    }
}
