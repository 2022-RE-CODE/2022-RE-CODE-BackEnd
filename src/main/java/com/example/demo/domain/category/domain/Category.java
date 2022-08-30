package com.example.demo.domain.category.domain;

import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.user.User;
import com.example.demo.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
@Getter
@Entity
public class Category extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Category(Long id, String name, User user, Post post) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.post = post;
    }

    public void confirmUser(User user) {
        user.addCategories(this);
        this.user = user;
    }

    public void confirmPost(Post post) {
        post.addCategories(this);
        this.post = post;
    }
}
