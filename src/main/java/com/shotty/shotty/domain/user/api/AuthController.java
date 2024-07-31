package com.shotty.shotty.domain.user.api;

import com.shotty.shotty.domain.user.dto.EncryptedUserDto;
import com.shotty.shotty.domain.user.dto.ResisterRequestDto;
import com.shotty.shotty.domain.user.dto.UserResponseDto;
import com.shotty.shotty.global.common.dto.ResponseDto;
import com.shotty.shotty.domain.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="회원가입 API",description = "회원가입 관련API")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "회원가입",description = "파라미터로 받은 등록폼으로 유저 생성 및 저장")
    @PostMapping("/api/auth/register")
    public ResponseEntity<ResponseDto<Null>> register(@Valid @RequestBody ResisterRequestDto resisterRequestDto) {
        EncryptedUserDto encryptedUserDto = EncryptedUserDto.from(resisterRequestDto);
        userService.register(encryptedUserDto);

        ResponseDto<Null> responseDto = new ResponseDto<>(
                (short)2010,
                "회원가입 성공하였습니다.",
                null
        );

        return ResponseEntity.ok(responseDto);
    }

}
