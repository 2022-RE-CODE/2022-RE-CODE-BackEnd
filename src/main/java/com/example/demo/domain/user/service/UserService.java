package com.example.demo.domain.user.service;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.user.web.dto.request.UserDeleteRequestDto;
import com.example.demo.domain.user.web.dto.request.UserPasswordRequestDto;
import com.example.demo.domain.user.web.dto.request.UserUpdateRequestDto;
import com.example.demo.domain.user.web.dto.request.UserJoinRequestDto;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.config.security.SecurityUtil;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private String email;

    @Transactional
    public Long join(UserJoinRequestDto request) throws CustomException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }

        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        if (emailService.verifyCode(request.getCheckEmailCode())) {
            throw new CustomException(ErrorCode.NOT_MATCH_CODE);
        }

        User user = userRepository.save(request.toEntity());
        user.encodePassword(passwordEncoder);
        user.addUserAuthority();

        return user.getId();
    }

    public UserResponseDto findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserResponseDto(user);
    }

    public List<UserResponseDto> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto request) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        user.updateNickname(request.getNickname());
        user.updateGitLink(request.getGitLink());
        user.updateBlogLink(request.getBlogLink());
        user.updatePosition(request.getPosition());

        return new UserResponseDto(user);
    }

    @Transactional
    public String updatePassword(UserPasswordRequestDto request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!Objects.equals(request.getNewPassword(), request.getCheckPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        user.updatePassword(passwordEncoder, request.getNewPassword());

        this.email = null;

        return "비밀번호가 정상적으로 변경되었습니다.";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transactional
    public String deleteUser(String matchedCode) throws Exception{
        if (SecurityUtil.getLoginUserEmail() == null) {
            throw new CustomException(ErrorCode.USER_NOT_LOGIN);
        }

        if (emailService.verifyCode(request.getCheckEmailCode())) {
            throw new CustomException(ErrorCode.NOT_MATCH_CODE);
        }

        String myAccount = SecurityUtil.getLoginUserEmail();
        userRepository.deleteByEmail(myAccount);

        return myAccount + "님 이용해주셔서 감사합니다.";
    }
}
