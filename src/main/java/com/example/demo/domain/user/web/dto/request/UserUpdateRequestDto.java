package com.example.demo.domain.user.web.dto.request;

import com.example.demo.domain.user.type.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    private final String nickname;
    private final String gitLink;
    private final String blogLink;
    private final String position;

    @Builder
    public UserUpdateRequestDto(String nickname, String gitLink, String blogLink, String position) {
        this.nickname = nickname;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
        this.position = position;
    }

}