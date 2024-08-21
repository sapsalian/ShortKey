package com.shotty.shotty.domain.user.dao;

import com.shotty.shotty.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByName(String username);
    Boolean existsByUserId(String userId);
}
