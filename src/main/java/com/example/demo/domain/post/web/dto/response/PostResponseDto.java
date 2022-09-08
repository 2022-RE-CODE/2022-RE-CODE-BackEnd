package com.example.demo.domain.post.web.dto.response;

import com.example.demo.domain.category.domain.Category;
import com.example.demo.domain.category.web.dto.response.CategoryResponseDto;
import com.example.demo.domain.post.domain.Post;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final Long writerId;
    private final int view;
    private final int likes;
    private final List<CategoryResponseDto> categories;
    private final List<PostCommentResponseDto> comments;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter().getNickname();
        this.writerId = post.getWriter().getId();
        this.view = post.getView();
        this.likes = post.getLikes().size();
        this.categories = post.getCategories().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
        this.comments = post.getComments().stream()
                .map(PostCommentResponseDto::new)
                .collect(Collectors.toList());
    }

}
