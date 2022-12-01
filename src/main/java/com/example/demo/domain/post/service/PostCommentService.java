package com.example.demo.domain.post.service;

import com.example.demo.domain.post.domain.PostComment;
import com.example.demo.domain.post.repository.PostCommentRepository;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.post.web.dto.response.PostCommentResponseDto;
import com.example.demo.domain.post.web.dto.request.PostCommentRequestDto;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserFacade userFacade;

    @Transactional
    public Long createComment(Long id, PostCommentRequestDto request) {
        postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        PostComment postComment = request.toEntity();

        postComment.confirmWriter(userFacade.getCurrentUser());

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

    @Transactional
    public Long delete(Long id) {
        PostComment postComment = postCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if (!postComment.getWriter().getEmail().equals(userFacade.getCurrentUser().getEmail())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        postCommentRepository.delete(postComment);

        return id;
    }
}
