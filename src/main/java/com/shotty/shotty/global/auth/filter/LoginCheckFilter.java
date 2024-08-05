package com.shotty.shotty.global.auth.filter;

import com.shotty.shotty.global.util.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            //"/api/auth","/swagger-ui","/v3/api-docs"
            "/"
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
        log.info("Access token: {},path= {}", accessToken,path);
        if(accessToken == null ) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Missing header");
            return;
        }

        //accessToken 검증
        try {
            jwtProvider.validateToken(accessToken);
        }catch (ExpiredJwtException  e) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Expired accessToken");
            return;
        }catch (Exception e) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Invalid accessToken");
            return;
        }

        //권한 확인### {Id,userRole}


        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
