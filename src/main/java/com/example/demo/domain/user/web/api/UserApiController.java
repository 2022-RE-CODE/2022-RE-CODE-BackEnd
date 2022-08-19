package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.user.web.dto.request.UserUpdateRequestDto;
import com.example.demo.domain.user.web.dto.request.UserFindByNicknameRequestDto;
import com.example.demo.domain.user.web.dto.request.UserJoinRequestDto;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto detail(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @GetMapping("/nickname")
    @ResponseStatus(HttpStatus.OK)
    public Result findAll(@RequestBody @Valid UserFindByNicknameRequestDto request) {
        List<UserResponseDto> users = userService.findByNickname(request.getNickname());
        return new Result(users.size(), users);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto update(@RequestBody UserUpdateRequestDto request) {
        return userService.updateUser(request);
    }

}
