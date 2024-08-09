package com.shotty.shotty.domain.post.dao;

import com.shotty.shotty.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    @Query(
            "UPDATE Post post " +
                    "SET post.author.id = :newUserId " +
                    "WHERE post.author.id = :oldUserId"
    )
    void updateAllByUserId(Long oldUserId, Long newUserId);

    @Query(
            "UPDATE Post post " +
                    "SET post.active = false " +
                    "WHERE post.author.id = :userId"
    )
    void deactivateAllByUserId(Long userId);
}
