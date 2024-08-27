package com.shotty.shotty.domain.bid.dao;

import com.shotty.shotty.domain.bid.domain.Bid;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import java.util.Optional;


@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    boolean existsByApplyId(Long applyId);
    void deleteByApplyId(Long applyId);

    Optional<Bid> findByApplyId(Long applyId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Bid b WHERE b.apply.influencer.id = :influencerId")
    void deleteAllByInfluencerId(@Param("influencerId") Long influencerId);

    @Query("select b from Bid b join fetch b.apply a join fetch a.influencer i join fetch i.user where b.accepted = true")
    List<Bid> findAcceptedBidsWithJoinFetch();
}
