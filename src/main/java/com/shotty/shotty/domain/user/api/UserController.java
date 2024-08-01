package com.shotty.shotty.domain.user.api;

import com.shotty.shotty.domain.user.application.UserService;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name= "사용자 정보 API 처리 컨트롤러",description = "사용자 정보 조회, 수정, 삭제를 위한 API를 처리하는 컨트롤러")
public class UserController {
    private final UserService userService;

    @GetMapping("/api/me")
    @Operation(summary = "현재 접속중인 사용자 정보 조회", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseDto<UserResponseDto>> getCurrentUser(@TokenId Long id) {
        UserResponseDto userResponseDto = userService.findById(id);

        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(
                2002,
                "내 정보 조회 성공",
                userResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }
}
