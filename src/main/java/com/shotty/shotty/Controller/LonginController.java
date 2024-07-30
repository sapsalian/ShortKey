package com.shotty.shotty.Controller;

import com.shotty.shotty.Domain.RefreshToken;
import com.shotty.shotty.Domain.User;
import com.shotty.shotty.Domain.UserRole;
import com.shotty.shotty.dto.UserDto;
import com.shotty.shotty.exception.LoginFailureException;
import com.shotty.shotty.repository.RefreshTokenRepository;
import com.shotty.shotty.service.JwtProvider;
import com.shotty.shotty.service.LoginService;
import com.shotty.shotty.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class LonginController {
    private final LoginService loginService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDto.loginRequest userDto) {
        log.info("로그인 컨트롤러");
        User user;
        //db조회
        try {
            user = loginService.login(userDto.getUserId(), userDto.getPassword());
        }catch (LoginFailureException e) {
            log.info(e.getMessage());
            LonginController.LoginResponse loginResponse = new LonginController.LoginFailLureResponse("4000", "아이디 또는 비밀번호가 잘못 되었습니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(loginResponse);
        }

        //로그인 성공
        String[] tokens = createTokens(user.getId(), user.getRole());
        LoginResponse loginResponse = new LoginSucessResponse("2000","로그인 성공",tokens);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loginResponse);
    }

    //refresh 토큰
    @PatchMapping("/refresh")
    public ResponseEntity<Object> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("refresh-token");
        Map<String, Object> claims = jwtProvider.getClaims(refreshToken);
        Long user_id = (Long)claims.get("user_id");
        UserRole userRole = UserRole.valueOf((String)claims.get("userRole"));
        refreshTokenRepository.findByUserId(user_id).orElseThrow(() ->
            new JwtException("유효하지않은")
        );
        String[] tokens = createTokens(user_id, userRole);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tokens);
    }


    //반환값 객체로 바꿔야할듯함
    private String[] createTokens(Long user_id, UserRole userRole)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put("user_id",user_id);
        claims.put("userRole",userRole);

        String accessToken = jwtProvider.getToken(claims, 2);
        String refreshToken = jwtProvider.getToken(claims, 48);
        log.info("accessToken" + accessToken);
        log.info("refreshToken" + refreshToken);
        refreshTokenRepository.save(new RefreshToken(user_id,refreshToken));
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



