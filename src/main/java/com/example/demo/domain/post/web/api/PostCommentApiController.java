package com.example.demo.domain.post.web.api;

import com.example.demo.domain.post.service.PostCommentService;
import com.example.demo.domain.post.web.dto.response.PostCommentResponseDto;
import com.example.demo.domain.post.web.dto.request.PostCommentRequestDto;
import com.example.demo.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class PostCommentApiController {

    private final PostCommentService postCommentService;

    @PostMapping("/create/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long create(@PathVariable Long id, @RequestBody @Valid PostCommentRequestDto request) {
        return postCommentService.createComment(id, request);
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result all(@PathVariable Long id) {
        List<PostCommentResponseDto> postComment = postCommentService.findAllDesc(id);
        return new Result(postComment.size(), postComment);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long delete(@PathVariable Long id) {
        return postCommentService.delete(id);
    }

}
