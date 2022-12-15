package com.example.demo.domain.category.web;

import com.example.demo.domain.category.service.CategoryService;
import com.example.demo.domain.post.web.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<PostResponseDto> getCategory(@RequestParam String categoryName) {
        return categoryService.findByCategoryName(categoryName);
    }
}
