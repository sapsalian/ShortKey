package com.shotty.shotty.Controller;

import com.shotty.shotty.dto.EncryptedUserDto;
import com.shotty.shotty.dto.ResisterRequestDto;
import com.shotty.shotty.dto.UserResponseDto;
import com.shotty.shotty.dto.common.ResponseDto;
import com.shotty.shotty.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<ResponseDto<UserResponseDto>> register(@Valid @RequestBody ResisterRequestDto resisterRequestDto) {
        EncryptedUserDto encryptedUserDto = EncryptedUserDto.from(resisterRequestDto);
        UserResponseDto userResponseDto = userService.register(encryptedUserDto);

        ResponseDto<UserResponseDto> responseDto = new ResponseDto<UserResponseDto>(
                (short)2010,
                "회원가입 성공하였습니다.",
                userResponseDto
        );

        return ResponseEntity.ok(responseDto);
    }

}
