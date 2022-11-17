package com.example.demo.domain.post.service;

import com.example.demo.domain.category.service.CategoryService;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.facade.PostFacade;
import com.example.demo.domain.post.web.dto.request.PostCreateRequestDto;
import com.example.demo.domain.post.web.dto.response.PostResponseDto;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.global.annotation.ServiceWithTransactionalReadOnly;
import com.example.demo.global.error.exception.RecodeException;
import com.example.demo.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class PostService {

    private final CategoryService categoryService;
    private final UserFacade userFacade; 
    private final PostFacade postFacade;

    @Transactional
    public Long createPost(PostCreateRequestDto request) {
        User user = userFacade.getCurrentUser();
        Post post = postFacade.save(request.toEntity());
        post.confirmWriter(user);

        request.getCategories()
                .forEach(c -> categoryService.createCategory(post, c.getName()));

        return post.getId();
    }

    @Transactional
    public PostResponseDto detail(Long id) {
        Post post = postFacade.findById(id);
        post.upView();

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> findAll(Pageable pageable) {
        return postFacade.findAll(pageable);
    }

    public List<PostResponseDto> findByTitle(String title, Pageable pageable) {
        return postFacade.findByTitle(title, pageable);
    }

    public List<PostResponseDto> findPostsViewDesc(Pageable pageable) {
        return postFacade.findPostsViewDesc(pageable);
    }

    public List<PostResponseDto> recentPosts(Pageable pageable) {
        return postFacade.recentPosts(pageable);
    }

    @Transactional
    public PostResponseDto update(Long id, PostCreateRequestDto request) {
        Post post = postFacade.findById(id);
        post.update(request.getTitle(), request.getContent());
        return new PostResponseDto(post);
    }

    @Transactional
    public void delete(Long id) {
        Post post = postFacade.findById(id);
        if (!post.getWriter().getEmail().equals(userFacade.getCurrentUser().getEmail())) {
            throw new RecodeException(ErrorCode.DONT_ACCESS_OTHER);
        }

        postFacade.delete(post);
    }

}
