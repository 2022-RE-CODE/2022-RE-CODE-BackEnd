package com.example.demo.domain.user;

import com.example.demo.domain.category.domain.Category;
import com.example.demo.domain.likes.domain.Likes;
import com.example.demo.domain.link.domain.Link;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.domain.PostComment;
import com.example.demo.domain.user.type.Position;
import com.example.demo.domain.user.type.Role;
import com.example.demo.global.entity.BaseTimeEntity;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Position position;

    private String gitLink;

    private String blogLink;
    private String imgPath;
    private String imgUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "writer")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Link> links = new ArrayList<>();

    @Builder
    public User(Long id, String email, String nickname, String password, Role role, Position position, String gitLink, String blogLink, List<Post> posts, List<Likes> likes, List<PostComment> comments) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.position = position;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
        this.posts = posts;
        this.likes = likes;
        this.comments = comments;
    }

    // Update User
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateGitLink(String gitLink) {
        this.gitLink = gitLink;
    }

    public void updateBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public void updatePosition(String position) {
        if (position.equals("FRONT")) {
            this.position = Position.FRONT;
        } else {
            this.position = Position.BACK;
        }
    }

    // auth
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void matchedPassword(PasswordEncoder passwordEncoder, User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }

    public void addUserAuthority() {
        this.role = Role.USER;
    }

    public void addPost(Post post) {
        this.getPosts().add(post);
    }

    public void addComment(PostComment comment) {
        this.comments.add(comment);
    }

    public void addCategories(Category category) {
        this.categories.add(category);
    }

    public void updateFile(String imgPath, String imgUrl) {
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }
}
