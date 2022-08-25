package com.example.demo.domain.post.repository;

import com.example.demo.domain.post.domain.PostComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @EntityGraph(attributePaths = {"writer", "post"})
    @Query("select pc from PostComment pc where pc.post.id = :id order by pc.createdAt")
    List<PostComment> findAllDesc(@Param("id") Long id);

}
