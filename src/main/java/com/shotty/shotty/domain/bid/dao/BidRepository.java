package com.shotty.shotty.domain.bid.dao;

import com.shotty.shotty.domain.bid.domain.Bid;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    boolean existsByApplyId(Long applyId);
    void deleteByApplyId(Long applyId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM bids WHERE id IN (" +
                "SELECT bids.id FROM bids " +
                "INNER JOIN applies ON bids.apply_id = applies.id " +
                "INNER JOIN influencers ON applies.influencer_id = influencers.influencer_id " +
                "WHERE influencers.influencer_id = :influencerId)",

                nativeQuery = true
    )
    void deleteAllByInfluencerId(@Param("influencerId") Long influencerId);
}
