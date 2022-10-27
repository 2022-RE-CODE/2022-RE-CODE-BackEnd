package com.example.demo.domain.user.service.validate;

import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.user.service.EmailService;
import com.example.demo.domain.user.web.dto.request.UserJoinRequestDto;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateUser {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @RequiredArgsConstructor
    class ValidateSignUpUser {
        private final ValidateUser validateUser;
        public void validateSignUpUser(UserJoinRequestDto request) {
            comparePassword(request.getPassword(), request.getCheckPassword());
            compareEmailCode(request.getCheckEmailCode());
            isAlreadyExistsUser(request.getEmail());
        }
    }

    public void validateSignUpUser(UserJoinRequestDto request) {
        ValidateSignUpUser validateSignUpUser = new ValidateSignUpUser(this);
        validateSignUpUser.validateSignUpUser(request);
    }

    public static void comparePassword(String password, String checkPassword) {
        if (!password.equals(checkPassword)) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    public void compareEmailCode(String code) {
        if (emailService.verifyCode(code)) {
            throw new CustomException(ErrorCode.NOT_MATCH_CODE);
        }
    }

    public void isAlreadyExistsUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }
    }
}
