package com.shotty.shotty.domain.influencer.dao;

import com.shotty.shotty.domain.influencer.domain.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {

}
