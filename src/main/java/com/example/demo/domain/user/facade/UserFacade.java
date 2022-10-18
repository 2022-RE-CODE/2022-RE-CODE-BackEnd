package com.example.demo.domain.user.facade;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.config.security.SecurityUtil;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentUser().getUser().getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));
    }
}
