package com.example.demo.domain.likes.web.api;

import com.example.demo.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/likes")
@RestController
public class LikesApiController {

    private final LikesService likesService;

    @PutMapping("/{id}")
    public String likes(@PathVariable long id) {
        return likesService.likes(id);
    }

}