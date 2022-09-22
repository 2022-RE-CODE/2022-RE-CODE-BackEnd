package com.example.demo.domain.post.web.dto.response;

import com.example.demo.domain.category.web.dto.response.CategoryResponseDto;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import lombok.Getter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private final Long postId;
    private final String title;
    private final String content;
    private final int view;
    private final int likes;
    private final String createMinutesAgo;
    private final List<CategoryResponseDto> categories;

    private final UserResponseDto user;
    private final List<PostCommentResponseDto> comments;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.view = post.getView();
        this.likes = post.getLikes().size();
        this.user = new UserResponseDto(post.getWriter());
        this.categories = post.getCategories().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
        this.comments = post.getComments().stream()
                .map(PostCommentResponseDto::new)
                .collect(Collectors.toList());

        this.createMinutesAgo = ChronoUnit.MINUTES.between(post.getCreatedAt(), LocalDateTime.now()) + "분전";
    }

}
