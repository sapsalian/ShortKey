package com.shotty.shotty.global.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shotty.shotty.global.common.dto.ResponseDto;
import com.shotty.shotty.global.util.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter implements Filter {
    private final JwtProvider jwtProvider;
    private static final List<String> whiteList = Arrays.asList(
            "/api/auth/register","/api/auth/login",
            "/api/influencers",
            "/swagger-ui","/v3/api-docs"
            //"/"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if( whiteList.stream().anyMatch(path::startsWith)){
            filterChain.doFilter(request, response);
            log.info("화이트리스트: path= {}", path);
            return;
        }
        //토큰 여부 확인
        String accessToken = getAccessToken(httpRequest);
        
        log.info("토큰 여부 확인: Access token: {},path= {}", accessToken,path);
        if(accessToken == null ) {
            setResponse(httpResponse,HttpServletResponse.SC_UNAUTHORIZED,4010,"missing header");
            return;
        }

        //accessToken 검증
        log.info("accessToken 검증");
        try {
            jwtProvider.validateToken(accessToken);
        }catch (ExpiredJwtException  e) {
            setResponse(httpResponse,HttpServletResponse.SC_BAD_REQUEST,4002,"Expired accessToken");
            return;
        }catch (Exception e) {
            setResponse(httpResponse,HttpServletResponse.SC_BAD_REQUEST,4003,"Invalid access token");
            return;
        }

        //권한 확인### {Id,userRole}


        filterChain.doFilter(request, response);
    }

    private void setResponse(HttpServletResponse httpResponse,int httpStatus,int code,String statusMsg) throws IOException {
        httpResponse.setStatus(httpStatus);
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("utf-8");
        ResponseDto<Null> responseDto = new ResponseDto<>(
                code,
                statusMsg,
                null
        );
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(responseDto);
        httpResponse.getWriter().write(responseJson);
    }

    private String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
