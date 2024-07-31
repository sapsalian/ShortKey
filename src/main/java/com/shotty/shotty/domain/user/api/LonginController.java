package com.shotty.shotty.domain.user.api;

import com.shotty.shotty.global.auth.exception.custom_exception.InvalidRefreshTokenException;
import com.shotty.shotty.global.auth.entity.RefreshToken;
import com.shotty.shotty.domain.user.domain.User;
import com.shotty.shotty.domain.user.enums.UserRoleEnum;
import com.shotty.shotty.domain.user.dto.UserDto;
import com.shotty.shotty.global.common.dto.ResponseDto;
import com.shotty.shotty.global.auth.dao.RefreshTokenRepository;
import com.shotty.shotty.global.util.JwtProvider;
import com.shotty.shotty.domain.user.application.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "로그인 API")
@Slf4j
public class LonginController {
    private final LoginService loginService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Operation(summary = "로그인", description = "파라미터로 받은 UserDto를 통해 DB조회 및 JWT토큰 발급")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<TokenBundle>> login(@RequestBody UserDto.loginRequest userDto) {
        log.info("로그인 컨트롤러");
        //db조회
        User user = loginService.login(userDto.getUserId(), userDto.getUserPassword());
        //로그인 성공
        TokenBundle tokenBundle = createTokens(user.getId(), user.getRole());


        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).orElse(new RefreshToken(user.getId(), null));

        refreshToken.setRefreshToken(tokenBundle.refreshToken());
        refreshTokenRepository.save(refreshToken);


        ResponseDto<TokenBundle> loginResponse = new ResponseDto<TokenBundle>(2000,"로그인 성공", tokenBundle);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loginResponse);
    }

    //refresh 토큰
    @Operation(summary = "토큰 재발급",description = "refresh토큰의 유효성 검증 및 토큰 재발급")
    @PatchMapping("/refresh")
    public ResponseEntity<ResponseDto<TokenBundle>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("Authorization").substring(7);
        log.info("refreshToken"+refreshToken);
        Map<String, Object> claims = jwtProvider.getClaims(refreshToken);
        Long user_id = Long.valueOf(claims.get("user_id").toString());
        UserRoleEnum userRole = UserRoleEnum.valueOf((String)claims.get("userRole"));

        RefreshToken originalRefreshToken = refreshTokenRepository.findByUserId(user_id).
                orElseThrow(() ->new InvalidRefreshTokenException("일치하는 refreshToken이 없음"));

        if (!originalRefreshToken.getRefreshToken().equals(refreshToken)) {
            throw new InvalidRefreshTokenException("Refresh token이 일치하지 않음.");
        }

        TokenBundle tokenBundle = createTokens(user_id, userRole);

        originalRefreshToken.setRefreshToken(tokenBundle.refreshToken());
        refreshTokenRepository.save(originalRefreshToken);

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
        return new TokenBundle(accessToken, refreshToken);
    }

    private record TokenBundle (
    String accessToken,
    String refreshToken
    ) {}

}



