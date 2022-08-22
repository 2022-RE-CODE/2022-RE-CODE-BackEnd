package com.example.demo.domain.user.web.dto.response;

import lombok.Getter;

@Getter
public class EmailCodeCheckResponsesDto {

    private final boolean status;

    public EmailCodeCheckResponsesDto(boolean status) {
        this.status = status;
    }

}
