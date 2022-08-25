package com.example.demo.domain.post.service;

import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.post.web.dto.PostCreateRequestDto;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.config.security.SecurityUtil;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(PostCreateRequestDto request) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        Post post = postRepository.save(request.toEntity());
        post.confirmWriter(user);

        return post.getId();
    }


}
