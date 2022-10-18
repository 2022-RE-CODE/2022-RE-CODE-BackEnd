package com.example.demo.domain.likes.service;

import com.example.demo.domain.likes.domain.Likes;
import com.example.demo.domain.likes.web.api.repository.LikesRepository;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserFacade userFacade;

    @Transactional
    public String likes(long postId) {
        User user = userFacade.getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        List<Likes> collect = post.getLikes().stream()
                .filter(l -> Objects.equals(l.getUser().getId(), user.getId()))
                .collect(Collectors.toList());

        if (collect.size() == 0) {
            likesRepository.likes(postId, user.getId());
            return "좋아요";
        } else {
            likesRepository.unLikes(postId, user.getId());
            return "좋아요 취소";
        }
    }
}
