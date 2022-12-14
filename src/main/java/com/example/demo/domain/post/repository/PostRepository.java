package com.example.demo.domain.post.repository;

import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.web.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"writer"})
    @Query("select m from Post m where m.title like %:title%")
    Page<Post> findByTitle(@Param("title") String title, Pageable pageable);

    @EntityGraph(attributePaths = {"writer"})
    @Query("select m from Post m order by m.view desc")
    Page<Post> findAllByPostViewDesc(Pageable pageable);

    @EntityGraph(attributePaths = {"writer"})
    @Query("select m from Post m order by m.createdAt desc")
    Page<Post> recentPosts(Pageable pageable);

}
