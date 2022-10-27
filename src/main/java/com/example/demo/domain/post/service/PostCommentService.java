package com.example.demo.domain.post.service;

import com.example.demo.domain.post.domain.PostComment;
import com.example.demo.domain.post.facade.PostCommentFacade;
import com.example.demo.domain.post.facade.PostFacade;
import com.example.demo.domain.post.web.dto.request.PostCommentRequestDto;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.global.annotation.ServiceWithTransactionalReadOnly;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class PostCommentService {

    private final PostCommentFacade postCommentFacade;
    private final PostFacade postFacade;
    private final UserFacade userFacade;

    @Transactional
    public void createComment(Long id, PostCommentRequestDto request) {
        postFacade.findById(id);

        PostComment postComment = request.toEntity();
        postComment.confirmWriter(userFacade.getCurrentUser());

        postComment.confirmPost(postFacade.findById(id));
        postCommentFacade.save(postComment);
    }

    @Transactional
    public void delete(Long id) {
        PostComment postComment = postCommentFacade.findById(id);
        if (!postComment.getWriter().getEmail().equals(userFacade.getCurrentUser().getEmail())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        postCommentFacade.delete(postComment);
    }
}
