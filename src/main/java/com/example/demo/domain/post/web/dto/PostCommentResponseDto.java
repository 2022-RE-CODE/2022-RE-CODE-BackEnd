package com.example.demo.domain.post.web.dto;

import com.example.demo.domain.post.domain.PostComment;
import lombok.Getter;

@Getter
public class PostCommentResponseDto {

    private final Long id;
    private final String comment;
    private final String nickname;

    public PostCommentResponseDto(PostComment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.nickname = comment.getWriter().getNickname();
    }

}
