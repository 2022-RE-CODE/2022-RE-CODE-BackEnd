package com.example.demo.domain.post.domain;

import com.example.demo.domain.category.domain.Category;
import com.example.demo.domain.likes.domain.Likes;
import com.example.demo.domain.user.User;
import com.example.demo.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int view = 1;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Post(Long id, User writer, String title, String content, int view, List<Likes> likes) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.view = view;
        this.likes = likes;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void upView() {
        this.view += 1;
    }

    public void confirmWriter(User writer) {
        this.writer = writer;
        writer.addPost(this);
    }

    public void confirmComment(PostComment comment) {
        this.comments.add(comment);
    }

    public void addCategories(Category category) {
        this.categories.add(category);
    }
}
