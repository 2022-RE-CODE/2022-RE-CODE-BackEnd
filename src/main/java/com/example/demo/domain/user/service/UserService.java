package com.example.demo.domain.user.service;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.domain.user.service.validate.ValidateUser;
import com.example.demo.domain.user.web.dto.request.UserPasswordRequestDto;
import com.example.demo.domain.user.web.dto.request.UserUpdateRequestDto;
import com.example.demo.domain.user.web.dto.request.UserJoinRequestDto;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.annotation.ServiceWithTransactionalReadOnly;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.file.FileResponseDto;
import com.example.demo.global.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;
    private final UserFacade userFacade;
    private final ValidateUser validateUser;

    @Transactional
    public void join(UserJoinRequestDto request) throws CustomException {
        validateUser.validateSignUpUser(request);
        userFacade.save(request.toEntity());
        addSecurity(userFacade.getCurrentUser());
    }

    private void addSecurity(User user) {
        user.encodePassword(passwordEncoder);
        user.addUserAuthority();
    }

    public UserResponseDto getCurrentUser() {
        return new UserResponseDto(userFacade.getCurrentUser());
    }

    public UserResponseDto findUser(Long id) {
        return new UserResponseDto(userFacade.findById(id));
    }

    public List<UserResponseDto> findByNickname(String nickname) {
        return userFacade.findByNickname(nickname);
    }


    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto request) {
        User user = userFacade.getCurrentUser();
        user.updateNickname(request.getNickname());
        user.updateGitLink(request.getGitLink());
        user.updateBlogLink(request.getBlogLink());
        user.updatePosition(request.getPosition());

        return new UserResponseDto(user);
    }

    @Transactional
    public void updatePassword(UserPasswordRequestDto request) {
        ValidateUser.comparePassword(request.getNewPassword(), request.getCheckPassword());

        User user = userFacade.getCurrentUser();
        user.updatePassword(passwordEncoder, request.getNewPassword());
    }

    @Transactional
    public void deleteUser(String matchedCode) {
        validateUser.compareEmailCode(matchedCode);

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
