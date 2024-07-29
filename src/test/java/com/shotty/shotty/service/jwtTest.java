package com.shotty.shotty.service;

import com.shotty.shotty.Domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
public class jwtTest {
    @Autowired JwtProvider jwtProvider;

    @Value("${jwt.secretKey}")
    private String secretKeyPlain;

    @Test
    public void 시크릿키() throws Exception{
        String keyBase64 = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64.getBytes());
        System.out.println("keyBase64 = " + keyBase64);
        System.out.println("secretKey = " + secretKey);
        Assertions.assertThat(secretKey).isNotNull();
    }

    @Test
    public void JWT() throws Exception{
        //given
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberid", "test");
        claims.put("role", UserRole.ADVERTISER);

        //when
        String token = jwtProvider.getToken(claims, 120);

        //then
        Map<String, Object> getClaim = jwtProvider.getClaims(token);
        Assertions.assertThat(getClaim.get("memberid")).isEqualTo("test");

        System.out.println("token = " + token);
    }

    @Test
    public void 토큰유효성() throws Exception{
        //given
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberid", "test");
        claims.put("role", UserRole.ADVERTISER);
        //when
        String accessToken = jwtProvider.getToken(claims, -1);

        //then
        System.out.println("accessToken = " + accessToken);

//        Assertions.assertThatThrownBy(()->Jwts.parserBuilder()
//                .setSigningKey(jwtProvider.getSecretKey())
//                .build()
//                .parseClaimsJws(accessToken))
//                .isInstanceOf(ExpiredJwtException.class);


        Assertions.assertThat(jwtProvider.validateToken(accessToken)).isEqualTo(false);
    }
}
