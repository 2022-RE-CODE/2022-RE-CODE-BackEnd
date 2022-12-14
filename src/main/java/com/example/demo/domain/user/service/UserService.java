package com.example.demo.domain.user.service;

import com.example.demo.domain.link.presentation.dto.res.LinkUserResponseDto;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.domain.user.web.dto.request.UserPasswordRequestDto;
import com.example.demo.domain.user.web.dto.request.UserUpdateRequestDto;
import com.example.demo.domain.user.web.dto.request.UserJoinRequestDto;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import com.example.demo.global.file.FileResponseDto;
import com.example.demo.global.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final S3Uploader s3Uploader;
    private final UserFacade userFacade;

    @Transactional
    public Long join(UserJoinRequestDto request) throws CustomException {
        userFacade.isAlreadyExistsUser(request.getEmail());

        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        if (emailService.verifyCode(request.getCheckEmailCode())) {
            throw new CustomException(ErrorCode.CODE_NOT_MATCH);
        }

        User user = userFacade.save(request.toEntity());
        user.encodePassword(passwordEncoder);
        user.addUserAuthority();

        return user.getId();
    }

    public UserResponseDto getCurrentUser() {
        return new UserResponseDto(userFacade.getCurrentUser());
    }

    public LinkUserResponseDto findUser(Long id) {
        User user = userFacade.findById(id);
        return new LinkUserResponseDto(user, user.getLinks());
    }

    public List<UserResponseDto> findByNickname(String nickname) {
        return userFacade.findByNickname(nickname);
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto request) {
        User user = userFacade.getCurrentUser();
        user.updateNickname(request.getNickname());
        user.updatePosition(request.getPosition());

        return new UserResponseDto(user);
    }

    @Transactional
    public void updatePassword(UserPasswordRequestDto request) {
        User user = userFacade.getCurrentUser();

        if (!Objects.equals(request.getNewPassword(), request.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.updatePassword(passwordEncoder, request.getNewPassword());
    }

    @Transactional
    public void deleteUser(String matchedCode) {
        if (emailService.verifyCode(matchedCode)) {
            throw new CustomException(ErrorCode.CODE_NOT_MATCH);
        }

        String email = userFacade.getCurrentUser().getEmail();
        userFacade.delete(email);
    }

    @Transactional
    public void updateImg(MultipartFile multipartFile) throws IOException {
        User user = userFacade.getCurrentUser();

        FileResponseDto fileResponseDto = s3Uploader.saveFile(multipartFile);
        user.updateFile(fileResponseDto.getImgPath(), fileResponseDto.getImgUrl());
    }
}
