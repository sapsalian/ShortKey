package com.shotty.shotty.domain.apply.dao;

import com.shotty.shotty.domain.apply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply,Long> {
    Optional<Apply> findByInfluencerId(Long influencer_id);

    Optional<Apply> findByInfluencerIdAndPostId(Long influencer_id, Long post_id);

    List<Apply> findAllByInfluencerId(Long influencerId);

    @Query(
            "DELETE apply FROM Apply apply WHERE apply.id IN ( " +
                    "SELECT apply.id FROM Apply apply " +
                    "INNER JOIN Influencer influencer ON apply.influencer_id = influencer.id " +
                    "WHERE influencer.id = :influencerId" +
                    ")"
    )
    void deleteByInfluencerId(@Param("influencerId") Long influencerId);
}
