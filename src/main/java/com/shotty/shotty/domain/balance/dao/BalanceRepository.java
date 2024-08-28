package com.shotty.shotty.domain.balance.dao;

import com.shotty.shotty.domain.balance.domain.Balance;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    @Transactional
    @Lock(LockModeType.WRITE)
    Optional<Balance> findByUserId(Long user_id);
}
