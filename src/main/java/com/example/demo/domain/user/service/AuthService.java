package com.example.demo.domain.user.service;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.domain.user.web.dto.request.LoginRequestDto;
import com.example.demo.domain.user.web.dto.response.TokenResponseDto;
import com.example.demo.global.config.redis.RedisService;
import com.example.demo.global.cookie.CookieProvider;
import com.example.demo.global.jwt.JwtTokenProvider;
import com.example.demo.global.jwt.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

import static com.example.demo.global.jwt.JwtProperties.ACCESS_TOKEN_VALID_TIME;
import static com.example.demo.global.jwt.JwtProperties.REFRESH_TOKEN_VALID_TIME;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtValidateService jwtValidateService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final CookieProvider cookieProvider;
    private final UserFacade userFacade;

    public TokenResponseDto login(LoginRequestDto request) {
        User user = userFacade.findByEmail(request.getEmail());
        user.matchedPassword(passwordEncoder, user, request.getPassword());

        final String accessToken = jwtTokenProvider.createAccessToken(request.getEmail());
        final String refreshToken = jwtTokenProvider.createRefreshToken(request.getEmail());
        redisService.setDataExpire(request.getEmail(), refreshToken, REFRESH_TOKEN_VALID_TIME);

        return jwtToCookies(accessToken, refreshToken);
    }

    public TokenResponseDto getNewAccessToken(String refreshToken) {
        jwtValidateService.validateRefreshToken(refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(jwtValidateService.getEmail(refreshToken));
        return jwtToCookies(accessToken, null);
    }

    public void logout(String accessToken) {
        User user = userFacade.getCurrentUser();
        jwtTokenProvider.logout(user.getEmail(), accessToken);
    }

    private TokenResponseDto jwtToCookies(String accessToken, String refreshToken) {
        Cookie accessTokenCookie = cookieProvider.createCookie("ACCESS-TOKEN", accessToken, ACCESS_TOKEN_VALID_TIME);
        Cookie refreshTokenCookie = cookieProvider.createCookie("REFRESH-TOKEN", refreshToken, REFRESH_TOKEN_VALID_TIME);

        return TokenResponseDto.builder()
                .accessToken(accessTokenCookie)
                .refreshToken(refreshTokenCookie)
                .build();
    }
}
