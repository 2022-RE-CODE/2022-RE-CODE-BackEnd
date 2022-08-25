package com.example.demo.domain.post.service;

import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.domain.PostComment;
import com.example.demo.domain.post.repository.PostCommentRepository;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.post.web.dto.PostCommentResponseDto;
import com.example.demo.domain.post.web.dto.request.PostCommentRequestDto;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.config.security.SecurityUtil;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createComment(Long id, PostCommentRequestDto request) {
        Optional<Post> byId = Optional.of(postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND)));

        PostComment postComment = request.toEntity();

        postComment
                .confirmWriter(userRepository
                        .findByEmail(SecurityUtil.getLoginUserEmail())
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN)));

        postComment
                .confirmPost(postRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND)));

        postCommentRepository.save(postComment);

        return postComment.getId();
    }

    public List<PostCommentResponseDto> findAllDesc(Long id) {
        return postCommentRepository.findAllDesc(id)
                .stream()
                .map(PostCommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
