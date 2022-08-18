package com.example.demo.domain.user.web.dto.response;

import com.example.demo.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String nickname;
    private final String role;
    private final String gitLink;
    private final String blogLink;
    private final String roles;
    private final String position;

    @Builder
    public UserResponseDto(Long id, String nickname, String role, String gitLink, String blogLink, String roles, String position) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
        this.roles = roles;
        this.position = position;
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.role = user.getRole().name();
        this.gitLink = user.getGitLink();
        this.blogLink = user.getBlogLink();
        this.roles = user.getRole().name();
        this.position = user.getPosition().name();
    }
}
