package com.example.demo.domain.likes.web.api.repository;

import com.example.demo.domain.likes.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(post_id, user_id) VALUES (:postId, :userId)", nativeQuery = true)
    void likes(@Param("postId") long postId, @Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE post_id = :postId AND user_id = :userId", nativeQuery = true)
    void unLikes(@Param("postId") long postId, @Param("userId") long userId);
}
