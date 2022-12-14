package com.example.demo.domain.link.presentation.dto.res;

import com.example.demo.domain.link.domain.Link;
import com.example.demo.domain.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 14.
 */

@Getter
public class LinkUserResponseDto {

    private final Long userId;
    private final String nickname;
    private final String role;
    private final String roles;
    private final String position;
    private final String img;
    private final List<LinkResponseDto> links;

    public LinkUserResponseDto(User user, List<Link> links) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.role = user.getRole().name();
        this.roles = user.getRole().name();
        this.position = user.getPosition().name();
        this.img = user.getImgUrl();
        this.links = links.stream()
                .map(LinkResponseDto::new)
                .collect(Collectors.toList());
    }

}
