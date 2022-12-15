package com.example.demo.domain.category.domain.repository;

import com.example.demo.domain.category.domain.Category;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.web.dto.response.PostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Post> findByName(String categoryName);
}
