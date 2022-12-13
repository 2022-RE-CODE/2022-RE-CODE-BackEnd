package com.example.demo.domain.link.domain.repository;

import com.example.demo.domain.link.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */
public interface LinkRepository extends JpaRepository<Link, Long> {
}
