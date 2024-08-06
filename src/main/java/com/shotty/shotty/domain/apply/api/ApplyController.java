package com.shotty.shotty.domain.apply.api;

import com.shotty.shotty.domain.apply.application.ApplyService;
import com.shotty.shotty.domain.apply.dto.ApplyRequestDto;
import com.shotty.shotty.domain.apply.dto.ApplyResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplyController {
    private final ApplyService applyService;
    @PostMapping("/applys")
    public ResponseEntity<ResponseDto<ApplyResponseDto>> apply(
            @TokenId Long user_id, @RequestParam Long post_id, @RequestBody ApplyRequestDto applyRequestDto) {
        ApplyResponseDto applyResponseDto = applyService.apply(user_id, post_id, applyRequestDto);
        ResponseDto<ApplyResponseDto> responseDto = new ResponseDto<>(
                2012,
                "신청 완료",
                applyResponseDto
        );
        return ResponseEntity.ok(responseDto);
    }
}
