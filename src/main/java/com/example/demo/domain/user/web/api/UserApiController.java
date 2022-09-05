package com.example.demo.domain.user.web.api;

import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.user.web.dto.request.*;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping
    public UserResponseDto findCurrentUser() {
        return userService.getCurrentUser();
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

    @PutMapping("/update/img")
    public void uploadImg(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        userService.updateImg(multipartFile);
    }

    @PutMapping("/update/password")
    public void updatePassword(@RequestBody @Valid UserPasswordRequestDto request) {
        userService.updatePassword(request);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String matchedCode) throws Exception {
        userService.deleteUser(matchedCode);
    }

}
