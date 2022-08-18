package com.example.demo.domain.user.service;

import com.example.demo.domain.user.web.dto.request.LoginRequestDto;
import com.example.demo.domain.user.web.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;

    // TODO
    public TokenResponseDto login(LoginRequestDto request) {
        return null;
    }
}
