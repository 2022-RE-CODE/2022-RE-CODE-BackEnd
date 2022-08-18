package com.example.demo.global.jwt;

import com.example.demo.global.config.redis.RedisService;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtValidateService {

    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;

    public String getEmail(String token) {
        return jwtTokenProvider.extractAllClaims(token)
                .get("email", String.class);
    }

    public String getRole(String token) {
        return jwtTokenProvider.extractAllClaims(token)
                .get("roles", String.class);
    }

    public void validateRefreshToken(String token) {
        if (redisService.getData(getEmail(token)) == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

}
