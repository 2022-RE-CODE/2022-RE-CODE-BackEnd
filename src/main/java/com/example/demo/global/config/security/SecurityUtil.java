package com.example.demo.global.config.security;

import com.example.demo.global.auth.AuthDetails;
import com.example.demo.global.auth.AuthDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static AuthDetails getCurrentUser() {
        return (AuthDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}