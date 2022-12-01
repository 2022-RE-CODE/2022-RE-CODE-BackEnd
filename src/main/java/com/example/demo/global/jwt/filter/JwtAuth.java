package com.example.demo.global.jwt.filter;

import com.example.demo.global.auth.AuthDetailsService;
import com.example.demo.global.exception.InvalidJwtException;
import com.example.demo.global.jwt.JwtProperties;
import com.example.demo.global.exception.ExpiredTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuth {

    private final AuthDetailsService authDetailsService;
    private final JwtProperties jwtProperties;
    private static final String REFRESH_KEY = "refresh_token";


    public Authentication authentication(String token) {
        Claims body = getJws(token).getBody();
        if (!isNotRefreshToken(token))
            throw InvalidJwtException.EXCEPTION;

        UserDetails userDetails = getDetails(body);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Jws<Claims> getJws(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidJwtException.EXCEPTION;
        }
    }

    public boolean isNotRefreshToken(String token) {
        return !REFRESH_KEY.equals(getJws(token).getHeader().get("typ").toString());
    }

    private UserDetails getDetails(Claims body) {
        if (body.get("role").toString() != null) {
            return authDetailsService
                    .loadUserByUsername(body.getSubject());
        }
        return null;
    }

}
