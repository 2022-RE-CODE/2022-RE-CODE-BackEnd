package com.example.demo.domain.post.facade;

import com.example.demo.domain.post.domain.PostComment;
import com.example.demo.domain.post.repository.PostCommentRepository;
import com.example.demo.domain.post.web.dto.response.PostCommentResponseDto;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostCommentFacade {

    private final PostCommentRepository postCommentRepository;

    public void save(PostComment postComment) {
        postCommentRepository.save(postComment);
    }

    public PostComment findById(Long id) {
        return postCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
    }

    public void delete(PostComment postComment) {
        postCommentRepository.delete(postComment);
    }
}
