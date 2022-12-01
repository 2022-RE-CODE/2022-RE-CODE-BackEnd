package com.example.demo.domain.user.service;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.user.web.dto.request.LoginRequestDto;
import com.example.demo.domain.user.web.dto.response.TokenResponseDto;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import com.example.demo.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDto execute(LoginRequestDto req) {
        validateLoginInfo(req);
        return jwtProvider.generateToken(req.getEmail(), "USER");
    }

    private void validateLoginInfo(LoginRequestDto req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.matchedPassword(passwordEncoder, user, req.getPassword());
    }
}
