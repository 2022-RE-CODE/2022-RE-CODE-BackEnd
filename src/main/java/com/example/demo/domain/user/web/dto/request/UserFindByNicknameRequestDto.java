package com.example.demo.domain.user.web.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserFindByNicknameRequestDto {

    @NotBlank
    private String nickname;
}
