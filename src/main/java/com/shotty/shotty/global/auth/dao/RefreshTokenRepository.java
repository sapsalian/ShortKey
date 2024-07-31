package com.shotty.shotty.global.auth.dao;

import com.shotty.shotty.global.auth.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Transactional
    public Optional<RefreshToken> findByUserId(Long user_id);
}
