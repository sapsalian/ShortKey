package com.shotty.shotty.domain.user.api;

import com.shotty.shotty.domain.user.application.UserService;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/me")
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
