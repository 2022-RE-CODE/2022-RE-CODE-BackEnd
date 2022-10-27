package com.example.demo.domain.user.facade;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.config.security.SecurityUtil;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.global.exception.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentUser().getUser().getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_LOGIN));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<UserResponseDto> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
