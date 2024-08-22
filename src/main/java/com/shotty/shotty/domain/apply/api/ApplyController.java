package com.shotty.shotty.domain.apply.api;

import com.shotty.shotty.domain.apply.application.ApplyService;
import com.shotty.shotty.domain.apply.dto.ApplyPatchRequestDto;
import com.shotty.shotty.domain.apply.dto.ApplyRequestDto;
import com.shotty.shotty.domain.apply.dto.ApplyResponseDto;
import com.shotty.shotty.domain.apply.dto.ApplySearchResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "지원 API", description = "모집공고에 대한 지원관련 API")
public class ApplyController {
    private final ApplyService applyService;

    @PostMapping("/applies")
    @Operation(summary = "지원 등록", description = "요청 폼으로 받은 정보로 지원등록")
    public ResponseEntity<ResponseDto<ApplyResponseDto>> apply(
            @Parameter(hidden = true) @TokenId Long user_id, @RequestParam Long post_id, @RequestBody ApplyRequestDto applyRequestDto) {
        ApplyResponseDto applyResponseDto = applyService.apply(user_id, post_id, applyRequestDto);
        ResponseDto<ApplyResponseDto> responseDto = new ResponseDto<>(
                2012,
                "신청 완료",
                applyResponseDto
        );
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/applies/{applyId}")
    @Operation(summary = "지원 상세 조회", description = "지원 id에 해당하는 지원 상세 정보 조회")
    public ResponseEntity<ResponseDto<ApplySearchResponseDto>> getApply(@PathVariable Long applyId, @Parameter(hidden = true) @TokenId Long userId) {
        ApplySearchResponseDto applySearchResponseDto = applyService.findApply(applyId, userId);
        ResponseDto<ApplySearchResponseDto> responseDto = new ResponseDto<>(
                2002,
                "지원 조회 성공",
                applySearchResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/applies")
    @Operation(summary = "지원 목록 조회",description = "쿼리 파라미터로 받은 인플루언서Id로 해당 인플루언서가 지원한 목록 조회")
    public ResponseEntity<ResponseDto<List<ApplySearchResponseDto>>> getApplies(
            @RequestParam Long influencer_id) {
        List<ApplySearchResponseDto> applies = applyService.findAppliesByInfluencerId(influencer_id);
        String statusMsg = "지원 목록 조회 성공";
        if (applies.isEmpty()) {
            statusMsg = "지원 목록 조회 성공(지원한 공고가 없습니다)";
        }

        ResponseDto<List<ApplySearchResponseDto>> response = new ResponseDto<>(
                2002,
                statusMsg,
                applies
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applies/my")
    @Operation(summary = "자신이 지원한 목록 조회",description = "토큰 ID로 해당 인플루언서가 지원한 목록 조회")
    public ResponseEntity<ResponseDto<List<ApplySearchResponseDto>>> getMyApplies(
            @Parameter(hidden = true) @TokenId Long user_id) {
        List<ApplySearchResponseDto> applies = applyService.findAppliesByUserId(user_id);
        String statusMsg = "지원 목록 조회 성공";
        if (applies.isEmpty()) {
            statusMsg = "지원 목록 조회 성공(지원한 공고가 없습니다)";
        }

        ResponseDto<List<ApplySearchResponseDto>> response = new ResponseDto<>(
                2002,
                statusMsg,
                applies
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/applies/{id}")
    @Operation(summary = "지원 내용 수정",description = "수정 폼을 통해 지원 내용 수정")
    public ResponseEntity<ResponseDto<ApplyResponseDto>> patchApply(
           @Parameter(hidden = true) @TokenId Long user_id, @RequestBody ApplyPatchRequestDto applyPatchRequestDto, @PathVariable("id") Long apply_id) {
        ApplyResponseDto applyResponseDto = applyService.patch(user_id,apply_id, applyPatchRequestDto);
        ResponseDto<ApplyResponseDto> responseDto = new ResponseDto<>(
                2003,
                "지원 내용 수정 성공",
                applyResponseDto
        );
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/applies/{id}")
    @Operation(summary = "지원 취소",description = "패스 파라미터로 받은 지원id로 해당 지원 삭제")
    public ResponseEntity<ResponseDto<Null>> cancelApply(
            @Parameter(hidden = true) @TokenId Long user_id, @PathVariable("id") Long apply_id
    ) {
        applyService.cancel(user_id, apply_id);
        ResponseDto<Null> responseDto = new ResponseDto<>(
                2005,
                "지원 취소 성공",
                null
        );
        return ResponseEntity.ok(responseDto);
    }
}
