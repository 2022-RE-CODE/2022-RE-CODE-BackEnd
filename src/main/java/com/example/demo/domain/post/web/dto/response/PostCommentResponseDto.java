package com.example.demo.domain.post.web.dto.response;

import com.example.demo.domain.post.domain.PostComment;
import lombok.Getter;

@Getter
public class PostCommentResponseDto {

    private final Long userId;
    private final Long commentId;
    private final String comment;
    private final String nickname;

    public PostCommentResponseDto(PostComment comment) {
        this.userId = comment.getWriter().getId();
        this.commentId = comment.getId();
        this.comment = comment.getComment();
        this.nickname = comment.getWriter().getNickname();
    }

}
