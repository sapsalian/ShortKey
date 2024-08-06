package com.shotty.shotty.domain.apply.dao;

import com.shotty.shotty.domain.apply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply,Long> {
}
