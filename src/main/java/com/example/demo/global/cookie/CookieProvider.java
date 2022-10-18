package com.example.demo.global.cookie;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieProvider {

    public Cookie createCookie(String name, String value, long time) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) time);
        cookie.setPath("/");
        return cookie;
    }

}