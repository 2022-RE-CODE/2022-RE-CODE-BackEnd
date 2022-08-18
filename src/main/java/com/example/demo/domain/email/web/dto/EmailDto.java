package com.example.demo.domain.email.web.dto;

import com.example.demo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

    @NotBlank
    private String email;

    public User toEntity() {
        return User.builder()
                .email(email)
                .build();
    }
}
