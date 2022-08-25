package com.example.demo.domain.post.domain;

import com.example.demo.domain.user.User;
import com.example.demo.global.entity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post_comment")
@Entity
public class PostComment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User writer;

    @Builder
    public PostComment(String comment, Post post, User writer) {
        this.comment = comment;
        this.post = PostComment.this.post;
        this.writer = writer;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    //== 연관관계 편의 메서드 ==//
    public void confirmWriter(User writer) {
        this.writer = writer;
        writer.addComment(this);
    }

    public void confirmPost(Post post) {
        this.post = post;
        post.confirmComment(this);
    }

}
