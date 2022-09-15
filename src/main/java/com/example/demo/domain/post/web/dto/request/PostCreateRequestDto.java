package com.example.demo.domain.post.web.dto.request;

import com.example.demo.domain.category.domain.Category;
import com.example.demo.domain.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import org.junit.experimental.categories.Categories;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class PostCreateRequestDto {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    private final List<Category> categories;

    @Builder
    public PostCreateRequestDto(String title, String content, List<Category> categories) {
        this.title = title;
        this.content = content;
        this.categories = categories;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
