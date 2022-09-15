package com.example.demo.domain.post.web.dto.request;

import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.domain.PostComment;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostCommentRequestDto {

    @NotBlank
    private final String comment;
    private final Post post;

    @Builder
    public PostCommentRequestDto(String comment, Post post) {
        this.comment = comment;
        this.post = post;
    }

    public PostComment toEntity() {
        return PostComment.builder()
                .comment(comment)
                .post(post)
                .build();
    }

}