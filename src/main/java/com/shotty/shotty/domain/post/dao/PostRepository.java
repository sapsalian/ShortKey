package com.shotty.shotty.domain.post.dao;

import com.shotty.shotty.domain.post.domain.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByAuthorId(Long userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE posts " +
                    "SET posts.author_id = :newUserId " +
                    "WHERE posts.author_id = :oldUserId",
            nativeQuery = true
    )
    void updateAllByUserId(Long oldUserId, Long newUserId);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE posts " +
                    "SET posts.active = false " +
                    "WHERE posts.author_id = :userId",
            nativeQuery = true
    )
    void deactivateAllByUserId(Long userId);
}
