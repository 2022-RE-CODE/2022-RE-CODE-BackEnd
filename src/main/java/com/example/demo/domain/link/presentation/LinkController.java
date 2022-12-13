package com.example.demo.domain.link.presentation;

import com.example.demo.domain.link.presentation.dto.req.LinkCreateRequestDto;
import com.example.demo.domain.link.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/link")
public class LinkController {

    private final LinkService linkService;

    @PostMapping
    public Long createLink(@RequestBody LinkCreateRequestDto req) {
        return linkService.createLink(req);
    }
}
