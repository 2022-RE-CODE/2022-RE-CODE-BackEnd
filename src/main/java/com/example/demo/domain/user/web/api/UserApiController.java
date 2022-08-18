package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.user.web.dto.request.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Long join(@RequestBody @Valid UserJoinRequestDto request) {
        return userService.join(request);
    }

}
