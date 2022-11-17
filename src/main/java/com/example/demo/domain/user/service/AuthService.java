package com.example.demo.domain.user.service;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.domain.user.web.dto.request.LoginRequestDto;
import com.example.demo.domain.user.web.dto.response.TokenResponseDto;
import com.example.demo.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserFacade userFacade;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginReq) {
        User user = userFacade.getUserByEmail(loginReq.getEmail());
        user.matchedPassword(passwordEncoder, user, loginReq.getPassword());

        return generateToken(user.getEmail());
    }

    private TokenResponseDto generateToken(String email) {
        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshToken = jwtProvider.generateRefreshToken(email);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiredAt(jwtProvider.getAccessTokenExpiredTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiredAt(jwtProvider.getRefreshTokenExpiredTime())
                .build();
    }

}
