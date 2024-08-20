package com.shotty.shotty.domain.influencer.dao;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import com.shotty.shotty.domain.influencer.enums.Niche;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    Optional<Influencer> findByUserId(Long userId);

    @Query("select i from Influencer i join i.user u " +
            "where u.name like %:userName% " +
            "and (:niches is null or i.niche in :niches)")
    Page<Influencer> findAll(
            @Param("userName") String userName,
            @Param("niches") Niche[] niches,
            Pageable pageable);

}
