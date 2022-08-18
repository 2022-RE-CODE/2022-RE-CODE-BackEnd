package com.example.demo.domain.user.web.dto.request;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.type.Position;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "이메일 인증 코드는 필수 입력 값입니다.")
    private String checkEmailCode;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
    private String checkPassword;
    private String position;

    @Builder
    public UserJoinRequestDto(String email, String nickname, String password, String checkPassword, String position) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.checkPassword = checkPassword;
        this.position = position;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .position(Position.valueOf(position))
                .build();
    }

}