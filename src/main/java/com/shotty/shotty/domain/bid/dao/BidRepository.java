package com.shotty.shotty.domain.bid.dao;

import com.shotty.shotty.domain.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    boolean existsByApplyId(Long applyId);
}
