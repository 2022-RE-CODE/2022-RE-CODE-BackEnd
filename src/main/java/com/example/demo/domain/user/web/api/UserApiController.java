package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.user.web.dto.request.*;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public Long join(@RequestBody @Valid UserJoinRequestDto request) {
        return userService.join(request);
    }

    @GetMapping("/{id}")
    public UserResponseDto detail(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @GetMapping("/nickname")
    public Result<List<UserResponseDto>> findAll(@RequestParam String nickname) {
        List<UserResponseDto> users = userService.findByNickname(nickname);
        return new Result<>(users.size(), users);
    }

    @PutMapping("/update")
    public UserResponseDto update(@RequestBody UserUpdateRequestDto request) {
        return userService.updateUser(request);
    }

    @PutMapping("/update/password")
    public String updatePassword(@RequestBody @Valid UserPasswordRequestDto request) {
        return userService.updatePassword(request);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String matchedCode) throws Exception {
        return userService.deleteUser(matchedCode);
    }

}
