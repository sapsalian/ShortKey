package com.shotty.shotty.Controller;

import com.shotty.shotty.Domain.User;
import com.shotty.shotty.Domain.UserRole;
import com.shotty.shotty.dto.UserDto;
import com.shotty.shotty.exception.LoginFailureException;
import com.shotty.shotty.service.JwtProvider;
import com.shotty.shotty.service.LoginService;
import com.shotty.shotty.service.UserService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class LonginController {
    private final LoginService loginService;
    private final JwtProvider jwtProvider;


    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDto.loginRequest userDto) {
        User user;
        //db조회
        try {
            user = loginService.login(userDto.getEmail(), userDto.getPassword());
        }catch (LoginFailureException e) {
            log.info(e.getMessage());
            LonginController.LoginResponse loginResponse = new LonginController.LoginFailLureResponse("4001", "아이디 또는 비밀번호가 잘못 되었습니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(loginResponse);
        }

        //로그인 성공
        String[] tokens = createTokens(user.getId(), user.getRole());
        LoginResponse loginResponse = new LoginSucessResponse("2000","로그인 성공",tokens);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loginResponse);
    }

    //반환값 객체로 바꿔야할듯함
    private String[] createTokens(Long id, UserRole userRole)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",id);
        claims.put("userRole",userRole);

        String accessToken = jwtProvider.getToken(claims, 2);
        String refreshToken = jwtProvider.getToken(claims, 6);
        return new String[]{accessToken,refreshToken};
    }

    interface LoginResponse{}

    @Data
    @AllArgsConstructor
    private class LoginSucessResponse implements LoginResponse {
        private String code;
        private String statusMsg;

        //객체로 변환해야할듯#########################
        private String[] data;
    }

    @Data
    @AllArgsConstructor
    private class LoginFailLureResponse implements LoginResponse {
        private String code;
        private String statusMsg;
    }

}



