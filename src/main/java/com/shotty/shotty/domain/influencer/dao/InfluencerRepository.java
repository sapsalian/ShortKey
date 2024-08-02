package com.shotty.shotty.domain.influencer.dao;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    Optional<Influencer> findByUserId(Long userId);

    Page<Influencer> findAll(Pageable pageable);
}
