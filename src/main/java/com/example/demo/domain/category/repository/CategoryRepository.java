package com.example.demo.domain.category.repository;

import com.example.demo.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}