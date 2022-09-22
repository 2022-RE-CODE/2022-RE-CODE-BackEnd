package com.example.demo.domain.user.web.dto.response;

import com.example.demo.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long userId;
    private final String nickname;
    private final String role;
    private final String gitLink;
    private final String blogLink;
    private final String roles;
    private final String position;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.role = user.getRole().name();
        this.gitLink = user.getGitLink();
        this.blogLink = user.getBlogLink();
        this.roles = user.getRole().name();
        this.position = user.getPosition().name();
    }
}
