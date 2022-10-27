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

    public static void comparePassword(UserJoinRequestDto request) {
        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    public void compareEmailCode(UserJoinRequestDto request) {
        if (emailService.verifyCode(request.getCheckEmailCode())) {
            throw new CustomException(ErrorCode.NOT_MATCH_CODE);
        }
    }

    public void isAlreadyExistsUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }
    }
}
