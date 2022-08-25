package com.example.demo.domain.user;

import com.example.demo.domain.likes.domain.Likes;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.user.type.Position;
import com.example.demo.domain.user.type.Role;
import com.example.demo.global.entity.BaseTimeEntity;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
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

    private String email;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Position position;

    private String gitLink;

    private String blogLink;

    @OneToMany(mappedBy = "writer")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likes> likes = new ArrayList<>();

    @Builder
    public User(Long id, String email, String nickname, String password, Role role, Position position, String gitLink, String blogLink, List<Post> posts) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.position = position;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
        this.posts = posts;
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
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    public void addUserAuthority() {
        this.role = Role.ROLE_USER;
    }

    public void addPost(Post post) {
        this.getPosts().add(post);
    }
}
