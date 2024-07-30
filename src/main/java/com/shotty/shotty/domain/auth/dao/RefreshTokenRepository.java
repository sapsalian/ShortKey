package com.shotty.shotty.domain.auth.dao;

import com.shotty.shotty.domain.auth.domain.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Transactional
    public Optional<RefreshToken> findByUserId(Long user_id);
}
