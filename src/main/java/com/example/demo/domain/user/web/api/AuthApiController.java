package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.AuthService;
import com.example.demo.domain.user.service.UserTokenRefreshService;
import com.example.demo.domain.user.web.dto.request.LoginRequestDto;
import com.example.demo.domain.user.web.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;
    private final UserTokenRefreshService userTokenRefreshService;


    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid LoginRequestDto request) {
        return authService.execute(request);
    }

    @PutMapping("/refresh")
    public TokenResponseDto tokenRefresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return userTokenRefreshService.execute(refreshToken);
    }

}