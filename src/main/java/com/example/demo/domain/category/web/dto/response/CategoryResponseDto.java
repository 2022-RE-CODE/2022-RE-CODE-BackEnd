package com.example.demo.domain.category.web.dto.response;

import com.example.demo.domain.category.domain.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private final String name;

    public CategoryResponseDto(Category category) {
        this.name = category.getName();
    }

}
