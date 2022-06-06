package com.example.demo.domain.post.web.api;

import com.example.demo.domain.post.service.PostCommentService;
import com.example.demo.domain.post.web.dto.request.PostCommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class PostCommentApiController {

    private final PostCommentService postCommentService;

    @PostMapping("/create/{id}")
    public Long create(@PathVariable Long id, @RequestBody @Valid PostCommentRequestDto request) {
        return postCommentService.createComment(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        return postCommentService.delete(id);
    }

}
