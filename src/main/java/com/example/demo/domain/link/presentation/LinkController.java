package com.example.demo.domain.link.presentation;

import com.example.demo.domain.link.presentation.dto.req.LinkCreateRequestDto;
import com.example.demo.domain.link.presentation.dto.res.LinkResponseDetailDto;
import com.example.demo.domain.link.presentation.dto.res.LinkUserResponseDto;
import com.example.demo.domain.link.service.LinkService;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.entity.BaseTimeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/link")
public class LinkController extends BaseTimeEntity {

    private final LinkService linkService;

    @PostMapping
    public Long createLink(@RequestBody LinkCreateRequestDto req) {
        return linkService.createLink(req);
    }

    @GetMapping
    public List<LinkUserResponseDto> getLinks() {
        return linkService.getAll();
    }

    @GetMapping("/{linkId}")
    public LinkResponseDetailDto getLinkDetail(@PathVariable Long linkId) {
        return linkService.linkDetail(linkId);
    }

    @PutMapping("/{linkId}")
    public void updateLink(
            @PathVariable Long linkId,
            @RequestBody LinkCreateRequestDto req
    ) {
        linkService.updateLink(linkId, req);
    }

    @DeleteMapping("/{linkId}")
    public void deleteLink(
            @PathVariable Long linkId
    ) {
        linkService.deleteLink(linkId);
    }
}
