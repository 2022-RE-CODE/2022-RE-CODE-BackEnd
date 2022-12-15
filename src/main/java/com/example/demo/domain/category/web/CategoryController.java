package com.example.demo.domain.category.web;

import com.example.demo.domain.category.service.CategoryService;
import com.example.demo.domain.post.web.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 15.
 */

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{categoryName}")
    public List<PostResponseDto> getCategory(@PathVariable String categoryName) {
        return categoryService.findByCategoryName(categoryName);
    }
}
