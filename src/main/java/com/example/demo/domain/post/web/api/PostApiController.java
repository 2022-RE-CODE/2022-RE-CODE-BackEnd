package com.example.demo.domain.post.web.api;

import com.example.demo.domain.post.service.PostService;
import com.example.demo.domain.post.web.dto.request.PostCreateRequestDto;
import com.example.demo.domain.post.web.dto.response.PostResponseDto;
import com.example.demo.domain.post.web.dto.request.PostTitleRequestDto;
import com.example.demo.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public Long createPost(@RequestBody PostCreateRequestDto request) {
        return postService.createPost(request);
    }

    @GetMapping("/find/detail/{id}")
    public PostResponseDto detail(@PathVariable Long id) {
        return postService.detail(id);
    }

    @GetMapping("/find/title")
    public Result<List<PostResponseDto>> findByTitle(@RequestBody @Valid PostTitleRequestDto request,
                                                     @PageableDefault(size = 10)
                              Pageable pageable) {
        List<PostResponseDto> post = postService.findByTitle(request.getTitle(), pageable);
        return new Result<>(post.size(), post);
    }

    @GetMapping("/find/view")
    public Result<List<PostResponseDto>> pagePosts(
            @PageableDefault(size = 10)
            Pageable pageable) {

        List<PostResponseDto> post = postService.findPostsViewDesc(pageable);

        return new Result<>(post.size(), post);
    }

    @GetMapping("/find/all")
    public Result<List<PostResponseDto>> findAll(
            @PageableDefault(size = 10)
            Pageable pageable) {

        List<PostResponseDto> post = postService.findAll(pageable);

        return new Result<>(post.size(), post);
    }

    @GetMapping("/find/new")
    public Result<List<PostResponseDto>> newPosts(
            @PageableDefault(size = 10)
            Pageable pageable) {

        List<PostResponseDto> post = postService.recentPosts(pageable);

        return new Result<>(post.size(), post);
    }

    @PutMapping("/update/{id}")
    public PostResponseDto update(@PathVariable Long id, @RequestBody @Valid PostCreateRequestDto request) {
        return postService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }

}