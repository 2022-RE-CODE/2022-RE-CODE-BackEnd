package com.example.demo.domain.post.web.dto;

import com.example.demo.domain.post.domain.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final Long writerId;
    private final int view;
    private final int likes;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter().getNickname();
        this.writerId = post.getWriter().getId();
        this.view = post.getView();
        this.likes = post.getLikes().size();
    }

}
