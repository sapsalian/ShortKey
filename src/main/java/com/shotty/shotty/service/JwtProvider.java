package com.shotty.shotty.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtProvider {
    private SecretKey cachedSecretKey;

    @Value("${jwt.secretKey}")
    private String secretKeyPlain;

    private SecretKey createSecretKey() {
        String keyBase64 = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64.getBytes());
        return secretKey;
    }

    private SecretKey getSecretKey() {
        if(cachedSecretKey == null) { cachedSecretKey = createSecretKey(); }
        return cachedSecretKey;
    }

    public String getToken(Map<String, Object> claims,int hours) {
        Long now = new Date().getTime();
        Date accessTokenExpires = new Date(now + hours * 60 * 1000L);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(accessTokenExpires)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    // 유효성 검사
    public void validateToken(String token) throws Exception {
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
