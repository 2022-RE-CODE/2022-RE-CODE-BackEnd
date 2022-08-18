package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.AuthService;
import com.example.demo.domain.user.web.dto.request.LoginRequestDto;
import com.example.demo.domain.user.web.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto login(@RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

}
