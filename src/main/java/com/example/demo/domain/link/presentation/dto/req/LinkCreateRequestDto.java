package com.example.demo.domain.link.presentation.dto.req;

import com.example.demo.domain.link.domain.Link;
import lombok.Getter;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */

@Getter
public class LinkCreateRequestDto {

    private final String title;
    private final String url;

    public LinkCreateRequestDto(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Link toEntity() {
        return Link.builder()
                .title(title)
                .url(url)
                .build();
    }
}
