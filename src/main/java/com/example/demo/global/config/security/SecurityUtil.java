package com.example.demo.global.config.security;

import com.example.demo.global.auth.CustomUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
    public static CustomUserDetail getCurrentUser() {
        return (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
