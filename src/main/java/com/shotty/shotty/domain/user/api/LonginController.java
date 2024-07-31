package com.shotty.shotty.domain.user.api;

import com.shotty.shotty.global.auth.entity.RefreshToken;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import com.shotty.shotty.domain.user.dto.UserDto;
import com.shotty.shotty.global.common.dto.ResponseDto;
import com.shotty.shotty.global.auth.dao.RefreshTokenRepository;
import com.shotty.shotty.global.util.JwtProvider;
import com.shotty.shotty.domain.user.application.LoginService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<ResponseDto<TokenBundle>> login(@RequestBody UserDto.loginRequest userDto) {
        log.info("로그인 컨트롤러");
        //db조회
        User user = loginService.login(userDto.getUserId(), userDto.getUserPassword());
        //로그인 성공
        TokenBundle tokenBundle = createTokens(user.getId(), user.getRole());

        ResponseDto<TokenBundle> loginResponse = new ResponseDto<TokenBundle>(2000,"로그인 성공", tokenBundle);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loginResponse);
    }

    //refresh 토큰
    @PatchMapping("/refresh")
    public ResponseEntity<ResponseDto<TokenBundle>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("Authorization").substring(7);
        log.info("refreshToken"+refreshToken);
        Map<String, Object> claims = jwtProvider.getClaims(refreshToken);
        Long user_id = Long.valueOf(claims.get("user_id").toString());
        UserRole userRole = UserRole.valueOf((String)claims.get("userRole"));

        refreshTokenRepository.findByUserId(user_id).
                orElseThrow(() ->new InvalidRefreshTokenException("일치하는 refreshToken이 없음"));

        TokenBundle tokenBundle = createTokens(user_id, userRole);

        ResponseDto<TokenBundle> responseDto = new ResponseDto<>(2010, "토큰 재발급 성공", tokenBundle);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }


    //반환값 객체로 바꿔야할듯함
    private TokenBundle createTokens(Long user_id, UserRoleEnum userRoleEnum)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put("user_id",user_id);
        claims.put("userRole", userRoleEnum);

        String accessToken = jwtProvider.getToken(claims, 2);
        String refreshToken = jwtProvider.getToken(claims, 48);
        log.info("accessToken" + accessToken);
        log.info("refreshToken" + refreshToken);
        refreshTokenRepository.save(new RefreshToken(user_id,refreshToken));
        return new TokenBundle(accessToken, refreshToken);
    }

    private record TokenBundle (
    String accessToken,
    String refreshToken
    ) {}

}



