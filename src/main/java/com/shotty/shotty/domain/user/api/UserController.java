package com.shotty.shotty.domain.user.api;

import com.shotty.shotty.domain.user.application.UserService;
import com.shotty.shotty.domain.user.domain.UserPatch;
import com.shotty.shotty.domain.user.dto.UserPatchRequestDto;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/users/{id}")
    @Operation(summary = "id를 이용해 사용자 정보 조회")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findById(id);

        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(
                2002,
                "사용자 정보 조회 성공",
                userResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/api/users")
    @Operation(summary = "현재 로그인된 사용자 정보 수정")
    public ResponseEntity<ResponseDto<UserResponseDto>> editUser(@TokenId Long id, @RequestBody UserPatchRequestDto userPatchRequestDto) {
        UserPatch userPatch = UserPatch.from(userPatchRequestDto);
        UserResponseDto userResponseDto = userService.patch(id, userPatch);

        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(
                2003,
                "사용자 정보 수정 성공",
                userResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }
}
