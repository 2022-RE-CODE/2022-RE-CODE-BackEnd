package com.example.demo.domain.user.web.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class EmailCodeCheckRequestDto {

    @NotBlank
    private String code;

}
