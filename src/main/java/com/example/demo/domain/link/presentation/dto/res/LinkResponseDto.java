package com.example.demo.domain.link.presentation.dto.res;

import com.example.demo.domain.link.domain.Link;
import lombok.Getter;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */

@Getter
public class LinkResponseDto {

    private final Long linkId;
    private final Long userId;
    private final String title;
    private final String url;

    public LinkResponseDto(Link link) {
        this.linkId = link.getId();
        this.userId = link.getUser().getId();
        this.title = link.getTitle();
        this.url = link.getUrl();
    }
}
