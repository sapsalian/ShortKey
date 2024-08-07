package com.shotty.shotty.domain.apply.api;

import com.shotty.shotty.domain.apply.application.ApplyService;
import com.shotty.shotty.domain.apply.dto.ApplyRequestDto;
import com.shotty.shotty.domain.apply.dto.ApplyRegisterResponseDto;
import com.shotty.shotty.domain.apply.dto.ApplySearchResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import com.shotty.shotty.global.common.exception.custom_exception.NoSuchResourcException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<ResponseDto<ApplyRegisterResponseDto>> apply(
            @Parameter(hidden = true) @TokenId Long user_id, @RequestParam Long post_id, @RequestBody ApplyRequestDto applyRequestDto) {
        ApplyRegisterResponseDto applyRegisterResponseDto = applyService.apply(user_id, post_id, applyRequestDto);
        ResponseDto<ApplyRegisterResponseDto> responseDto = new ResponseDto<>(
                2012,
                "신청 완료",
                applyRegisterResponseDto
        );
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/applies")
    public ResponseEntity<ResponseDto<List<ApplySearchResponseDto>>> getApplies(
            @RequestParam Long influencer_id) {
        List<ApplySearchResponseDto> applies = applyService.findAppliesByInfluencerId(influencer_id);
        String statusMsg ="지원 목록 조회 성공";
        if (applies.isEmpty()) {
            statusMsg = "지원 목록 조회 성공(지원한 공고가 없습니다";
        }

        ResponseDto<List<ApplySearchResponseDto>> response = new ResponseDto<>(
                2002,
                statusMsg,
                applies
        );
        return ResponseEntity.ok(response);
    }
}
