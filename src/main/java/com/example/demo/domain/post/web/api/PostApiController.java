package com.example.demo.domain.post.web.api;

import com.example.demo.domain.post.service.PostService;
import com.example.demo.domain.post.web.dto.PostCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    
}
