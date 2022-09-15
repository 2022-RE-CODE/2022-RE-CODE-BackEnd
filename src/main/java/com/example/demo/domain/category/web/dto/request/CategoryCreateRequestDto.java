package com.example.demo.domain.category.web.dto.request;

import com.example.demo.domain.category.domain.Category;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CategoryCreateRequestDto {

    @NotNull
    private final String name;

    public CategoryCreateRequestDto(String name) {
        this.name = name;
    }

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
