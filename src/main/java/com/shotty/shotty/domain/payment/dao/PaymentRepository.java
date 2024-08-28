package com.shotty.shotty.domain.payment.dao;

import com.shotty.shotty.domain.payment.domain.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Payment p WHERE p.bid.apply.influencer.id = :influencerId")
    void deleteAllByInfluencerId(@Param("influencerId") Long influencerId);
}
