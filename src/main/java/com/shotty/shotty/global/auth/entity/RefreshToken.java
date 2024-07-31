package com.shotty.shotty.global.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue
    @Column(name = "refreshToken_id")
    private Long id;

    private Long userId;
    private String refreshToken;

    public RefreshToken() {
    }

    public RefreshToken(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

}
