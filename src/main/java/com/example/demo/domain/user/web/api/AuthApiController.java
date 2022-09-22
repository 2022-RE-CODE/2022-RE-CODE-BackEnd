package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.AuthService;
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

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid LoginRequestDto request) {
        return authService.login(request);
    }

    @PutMapping("/refresh")
    public TokenResponseDto getNewAccessToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("REFRESH-TOKEN");
        return authService.getNewAccessToken(refreshToken);
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        String accessToken = request.getHeader("ACCESS-TOKEN");
        authService.logout(accessToken);
    }

}