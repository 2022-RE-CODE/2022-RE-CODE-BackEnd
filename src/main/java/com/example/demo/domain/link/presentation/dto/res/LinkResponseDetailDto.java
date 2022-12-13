package com.example.demo.domain.link.presentation.dto.res;

import com.example.demo.domain.link.domain.Link;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 14.
 */

@Getter
public class LinkResponseDetailDto {

    private final Long linkId;
    private final String title;
    private final String url;
    private final UserResponseDto user;
    private final LocalDateTime updatedAt;

    public LinkResponseDetailDto(Link link) {
        this.linkId = link.getId();
        this.title = link.getTitle();
        this.url = link.getUrl();
        this.user = new UserResponseDto(link.getUser());
        this.updatedAt = link.getModifiedAt();
    }
}
