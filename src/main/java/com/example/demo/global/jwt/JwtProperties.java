package com.example.demo.global.jwt;

import java.time.Duration;

public class JwtProperties {
    public static final int ACCESS_TOKEN_VALID_TIME = (int) Duration.ofMinutes(30).toMillis();;
    public static final int REFRESH_TOKEN_VALID_TIME = (int) Duration.ofDays(14).toMillis();
    public static final String JWT_ACCESS = "ACCESS-TOKEN";
}
