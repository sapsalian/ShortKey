package com.shotty.shotty.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Slf4j
@Component
public class JwtProvider {
    private SecretKey cachedSecretKey;

    @Value("${jwt.secretKey}")
    private String secretKeyPlain;

    @PostConstruct
    private void init() {
        // 객체가 생성된 후 secretKeyPlain을 이용해 secretKey를 생성하고 캐시합니다.
        this.cachedSecretKey = createSecretKey();
    }

//    private SecretKey createSecretKey() {
//        System.out.println("createSecretKey" );
//        String keyBase64 = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
//        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64.getBytes());
//        System.out.println("secretKey = " + secretKey );
//        return secretKey;
//    }
//
//    private SecretKey getSecretKey() {
//        System.out.println("cachedSecretKey = " + cachedSecretKey);
//        if(cachedSecretKey == null) { cachedSecretKey = createSecretKey(); }
//        System.out.println("cachedSecretKey = " + cachedSecretKey);
//        return cachedSecretKey;
//    }

    private SecretKey createSecretKey() {
        System.out.println("createSecretKey");
        String keyBase64 = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64.getBytes());
        System.out.println("secretKey = " + secretKey);
        return secretKey;
    }

    public SecretKey getSecretKey() {
        System.out.println("cachedSecretKey = " + cachedSecretKey);
        return cachedSecretKey;
    }

    public String getToken(Map<String, Object> claims,int hours) {
        Long now = new Date().getTime();
        Date accessTokenExpires = new Date(now + hours * 60 * 60 * 1000L);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(accessTokenExpires)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    // 유효성 검사
    public void validateToken(String token) throws Exception {
        log.info("token:{}", token);
        log.info("secretKey:{}", getSecretKey());
        log.info("Encoded secretKey:{}", getSecretKey().getEncoded());
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    // claim 디코딩
    public Map<String ,Object> getClaims(String token)  {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
