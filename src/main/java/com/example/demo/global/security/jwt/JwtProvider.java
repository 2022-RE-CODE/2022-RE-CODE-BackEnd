package com.example.demo.global.security.jwt;

import com.example.demo.domain.auth.domain.RefreshToken;
import com.example.demo.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.demo.global.security.auth.AuthDetailsService;
import com.example.demo.global.security.jwt.exception.ExpiredJwtException;
import com.example.demo.global.security.jwt.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

import static com.example.demo.global.security.jwt.JwtConstants.*;
import static javax.xml.crypto.dsig.SignatureProperties.TYPE;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private String generateToken(String email, String type, Long exp) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .setSubject(email)
                .claim(TYPE, type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .compact();
    }

    public String generateAccessToken(String email) {
        return generateToken(email, ACCESS, jwtProperties.getAccessExp());
    }

    public String generateRefreshToken(String email) {
        String refreshToken = generateToken(email, REFRESH, jwtProperties.getRefreshExp());
        refreshTokenRepository.save(RefreshToken.builder()
                .email(email)
                .token(refreshToken)
                .timeToLive(jwtProperties.getRefreshExp())
                .build());

        return refreshToken;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearer = req.getHeader(jwtProperties.getHeader());
        log.info(RESOLVE_TOKEN, bearer);
        return parseToken(bearer);
    }

    private String parseToken(String bearer) {
        return bearer != null && bearer.startsWith(jwtProperties.getPrefix()) ?
                bearer.replace(jwtProperties.getPrefix(), EMPTY_STRING) :
                null;
    }

    public ZonedDateTime getAccessTokenExpiredTime() {
        return ZonedDateTime.now().plusSeconds(jwtProperties.getAccessExp());
    }

    public ZonedDateTime getRefreshTokenExpiredTime() {
        return ZonedDateTime.now().plusSeconds(jwtProperties.getRefreshExp());
    }

    public Authentication authentication(String token) {
        UserDetails userDetails = authDetailsService
                .loadUserByUsername(getTokenSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getTokenSubject(String token) {
        return Objects.requireNonNull(getTokenBody(token)).getSubject();
    }

    private Claims getTokenBody(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token).getBody();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw ExpiredJwtException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidJwtException.EXCEPTION;
        }
    }

    private Date getExpiredTime(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey().getBytes()).parseClaimsJws(token).getBody().getExpiration();
    }
}
