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
    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
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
                .claim("body", mapToJson(claims))
                .setExpiration(accessTokenExpires)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    // 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            return false;
        }

        return true;
    }

    // claim 디코딩
    public Map<String ,Object> getClaims(String token) {
        String body = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);
        //log.info(body);
        return jsonToMap(body);
    }

    private Object mapToJson(Map<String, Object> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private  Map<String, Object> jsonToMap(String jsonStr) {
        try {
            return new ObjectMapper().readValue(jsonStr, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
