package com.example.demo.domain.user.service;

import com.example.demo.domain.auth.domain.RefreshToken;
import com.example.demo.domain.auth.domain.RefreshTokenRepository;
import com.example.demo.domain.exception.RefreshTokenNotFoundException;
import com.example.demo.domain.user.web.dto.response.TokenResponseDto;
import com.example.demo.global.jwt.JwtProperties;
import com.example.demo.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserTokenRefreshService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    public TokenResponseDto execute(String refreshToken) {
        RefreshToken redisRefreshToken = refreshTokenRepository.findByToken(jwtProvider.parseToken(jwtProperties.getPrefix() + refreshToken))
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);
        return getNewTokens(redisRefreshToken);
    }

    private TokenResponseDto getNewTokens(RefreshToken redisRefreshToken) {
        String newRefreshToken = jwtProvider.generateToken(redisRefreshToken.getId(), "USER").getRefreshToken();
        redisRefreshToken.update(newRefreshToken, jwtProperties.getRefreshExp());

        String newAccessToken = jwtProvider.generateToken(redisRefreshToken.getId(), "USER").getAccessToken();
        return TokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiredAt(jwtProvider.getExpiredTime())
                .build();
    }
}
