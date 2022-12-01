package com.example.demo.domain.post.service;

import com.example.demo.domain.category.service.CategoryService;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.post.web.dto.request.PostCreateRequestDto;
import com.example.demo.domain.post.web.dto.response.PostResponseDto;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final UserFacade userFacade;

    @Transactional
    public Long createPost(PostCreateRequestDto request) {
        User user = userFacade.getCurrentUser();
        Post post = postRepository.save(request.toEntity());
        post.confirmWriter(user);

        request.getCategories()
                .forEach(c -> categoryService.createCategory(post, c.getName()));

        return post.getId();
    }

    @Transactional
    public PostResponseDto detail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        post.upView();

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> findByTitle(String title, Pageable pageable) {
        return postRepository.findByTitle(title, pageable)
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> findPostsViewDesc(Pageable pageable) {
        return postRepository.findAllByPostViewDesc(pageable)
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> recentPosts(Pageable pageable) {
        return postRepository.recentPosts(pageable)
                .stream()
                .filter(p -> ChronoUnit.MINUTES.between(p.getCreatedAt(), LocalDateTime.now()) < 1440)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto update(Long id, PostCreateRequestDto request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if (!Objects.equals(post.getWriter().getId(), userFacade.getCurrentUser().getId())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        post.update(request.getTitle(), request.getContent());
        return new PostResponseDto(post);
    }

    @Transactional
    public String delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if (!post.getWriter().getEmail().equals(userFacade.getCurrentUser().getEmail())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        postRepository.delete(post);

        return "정상적으로 삭제되었습니다.";
    }

}
