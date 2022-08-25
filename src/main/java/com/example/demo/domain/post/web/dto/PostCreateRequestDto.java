package com.example.demo.domain.post.web.dto;

import com.example.demo.domain.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostCreateRequestDto {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    @Builder
    public PostCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
