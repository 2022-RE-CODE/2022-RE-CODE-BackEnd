package com.example.demo.domain.post.web.api;

import com.example.demo.domain.post.service.PostService;
import com.example.demo.domain.post.web.dto.request.PostCreateRequestDto;
import com.example.demo.domain.post.web.dto.PostResponseDto;
import com.example.demo.domain.post.web.dto.request.PostTitleRequestDto;
import com.example.demo.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Long createPost(@RequestBody PostCreateRequestDto request) {
        return postService.createPost(request);
    }

    @GetMapping("/find/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto detail(@PathVariable Long id) {
        return postService.detail(id);
    }

    @GetMapping("/find/title")
    @ResponseStatus(HttpStatus.OK)
    public Result findByTitle(@RequestBody @Valid PostTitleRequestDto request,
                              @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                              Pageable pageable) {
        List<PostResponseDto> post = postService.findByTitle(request.getTitle(), pageable);
        return new Result(post.size(), post);
    }

    @GetMapping("/find/all")
    @ResponseStatus(HttpStatus.OK)
    public Result pagePosts(
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {

        List<PostResponseDto> managerPost = postService.all(pageable);

        return new Result(managerPost.size(), managerPost);
    }
}
