package com.example.demo.global.jwt;

import com.example.demo.domain.auth.domain.RefreshToken;
import com.example.demo.domain.auth.domain.RefreshTokenRepository;
import com.example.demo.domain.user.web.dto.response.TokenResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final String ACCESS_KEY = "access_token";
    private static final String REFRESH_KEY = "refresh_token";

    public TokenResponseDto generateToken(String id, String role) {
        String accessToken = jwtProperties.getPrefix() + " " + generateToken(id, role, ACCESS_KEY, jwtProperties.getAccessExp());
        String refreshToken = jwtProperties.getPrefix() + " " + generateToken(id, role, REFRESH_KEY, jwtProperties.getRefreshExp());

        refreshTokenRepository.save(RefreshToken.builder()
                .id(id)
                .token(refreshToken)
                .ttl(jwtProperties.getRefreshExp() * 1000)
                .build());

        return new TokenResponseDto(accessToken, refreshToken, getExpiredTime());
    }

    private String generateToken(String id, String role, String type, Long exp) {
        return Jwts.builder()
                .setSubject(id)
                .setHeaderParam("typ", type)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .setExpiration(
                        new Date(System.currentTimeMillis() + exp * 1000)
                )
                .setIssuedAt(new Date())
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(jwtProperties.getHeader());
        return parseToken(bearer);
    }

    public String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getPrefix())){
            return bearerToken.replace(jwtProperties.getPrefix(), "");
        }
        return null;
    }

    public ZonedDateTime getExpiredTime() {
        return ZonedDateTime.now().plusSeconds(jwtProperties.getRefreshExp());
    }

}
