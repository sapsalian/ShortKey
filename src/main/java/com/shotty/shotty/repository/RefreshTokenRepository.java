package com.shotty.shotty.repository;

import com.shotty.shotty.Domain.RefreshToken;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Transactional
    public Optional<RefreshToken> findByUserId(Long user_id);
}
