package com.example.demo.domain.user.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class UserPasswordRequestDto {

    @NotBlank
    private String newPassword;

    @NotBlank
    private String checkPassword;

}