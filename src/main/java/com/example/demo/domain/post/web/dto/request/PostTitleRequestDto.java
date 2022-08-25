package com.example.demo.domain.post.web.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class PostTitleRequestDto {

    @NotBlank
    private String title;

}
