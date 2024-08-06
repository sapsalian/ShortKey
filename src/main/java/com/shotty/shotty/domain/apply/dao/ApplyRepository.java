package com.shotty.shotty.domain.apply.dao;

import com.shotty.shotty.domain.apply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply,Long> {
    Optional<Apply> findByInfluencerId(Long influencer_id);
}
