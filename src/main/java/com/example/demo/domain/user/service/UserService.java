package com.example.demo.domain.user.service;

import com.example.demo.domain.email.service.EmailService;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.user.web.dto.request.UserJoinRequestDto;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public Long join(UserJoinRequestDto request) throws CustomException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }

        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        if (emailService.verifyCode(request.getCheckEmailCode())) {
            throw new CustomException(ErrorCode.NOT_MATCH_CODE);
        }

        User user = userRepository.save(request.toEntity());
        user.encodePassword(passwordEncoder);
        user.addUserAuthority();

        return user.getId();
    }

}
