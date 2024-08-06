package com.shotty.shotty.domain.bid.dao;

import com.shotty.shotty.domain.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
}
