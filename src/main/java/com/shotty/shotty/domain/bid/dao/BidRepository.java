package com.shotty.shotty.domain.bid.dao;

import com.shotty.shotty.domain.bid.domain.Bid;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    boolean existsByApplyId(Long applyId);
    void deleteByApplyId(Long applyId);

    @SQL(
            "DELETE bid FROM Bid bid " +
                    "INNER JOIN bid.apply apply ON bid.apply_id = apply.id " +
                    "INNER JOIN apply.influencer influencer ON apply.influencer_id = influencer.id " +
                    "WHERE :influencerId = influencer.id"
    )
    void deleteAllByInfluencerId(@Param("influencerId") Long influencerId);
}
