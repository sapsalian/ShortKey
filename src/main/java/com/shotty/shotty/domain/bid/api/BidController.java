package com.shotty.shotty.domain.bid.api;

import com.shotty.shotty.domain.bid.application.BidService;
import com.shotty.shotty.domain.bid.dto.BidRequestDto;
import com.shotty.shotty.domain.bid.dto.BidResponseDto;
import com.shotty.shotty.domain.bid.dto.ShortsIdRequestDto;
import com.shotty.shotty.domain.bid.dto.ShortsIdUploadDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "입찰 관련 API")
public class BidController {
    private final BidService bidService;

    @Operation(summary = "입찰 등록", description = "특정 지원에 대해 입찰하는 api")
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

    @Operation(summary = "업로드한 쇼츠 영상 id 등록", description = "실제로 업로드한 쇼츠의 id를 등록")
    @PatchMapping("/api/applies/{applyId}/shorts")
    public ResponseEntity<ResponseDto<Null>> uploadShortsId(
            @TokenId
            @Parameter(hidden = true)
            Long userId,

            @PathVariable
            Long applyId,

            @RequestBody
            ShortsIdRequestDto shortsIdRequestDto
    ) {
        ShortsIdUploadDto shortsIdUploadDto = ShortsIdUploadDto.of(applyId, userId, shortsIdRequestDto);

        bidService.updateShortsId(shortsIdUploadDto);

        ResponseDto<Null> responseDto = new ResponseDto<>(
                2007,
                "쇼츠 ID 등록 성공",
                null
        );

        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "입찰 승인", description = "업로드된 영상을 확인한 후 광고주의 최종 승인을 담당하는 api")
    @PostMapping("/api/applies/{applyId}/accept")
    public ResponseEntity<ResponseDto<Null>> acceptBid(
            @PathVariable
            Long applyId,

            @TokenId
            @Parameter(hidden = true)
            Long accepterId
    ) {
        bidService.acceptBid(accepterId, applyId);

        ResponseDto<Null> responseDto = new ResponseDto<>(
                2008,
                "광고주 승인 완료",
                null
        );

        return ResponseEntity.ok(responseDto);
    }
}
