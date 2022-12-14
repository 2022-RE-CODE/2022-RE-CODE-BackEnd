package com.example.demo.domain.link.service;

import com.example.demo.domain.link.domain.Link;
import com.example.demo.domain.link.facade.LinkFacade;
import com.example.demo.domain.link.presentation.dto.req.LinkCreateRequestDto;
import com.example.demo.domain.link.presentation.dto.res.LinkResponseDetailDto;
import com.example.demo.domain.link.presentation.dto.res.LinkUserResponseDto;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import com.example.demo.domain.user.web.dto.response.UserResponseDto;
import com.example.demo.global.error.exception.CustomException;
import com.example.demo.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LinkService {

    private final LinkFacade linkFacade;
    private final UserFacade userFacade;

    @Transactional
    public Long createLink(LinkCreateRequestDto req) {
        Link link = linkFacade.create(req.toEntity());
        link.confirmUser(userFacade.getCurrentUser());
        return link.getId();
    }

    @Transactional
    public void updateLink(Long linkId, LinkCreateRequestDto req) {
        Link link = linkFacade.findByLinkId(linkId);
        link.updateInfo(req);
    }

    @Transactional
    public void deleteLink(Long linkId) {
        Link link = linkFacade.findByLinkId(linkId);
        if (!Objects.equals(link.getUser().getId(), userFacade.getCurrentUser().getId())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        linkFacade.deleteByLinkId(linkId);
    }

    public List<LinkResponseDetailDto> linkDetail(Long linkId) {
        return linkFacade.findByUserId(linkId)
                .stream()
                .map(LinkResponseDetailDto::new)
                .collect(Collectors.toList());
    }

    public List<LinkUserResponseDto> getAll() {
        return userFacade.findAll()
                .stream()
                .map(user -> {
                    return new LinkUserResponseDto(user, user.getLinks());
                })
                .collect(Collectors.toList());
    }

    public List<LinkResponseDetailDto> getMine() {
        return linkFacade.getMyLinks()
                .stream()
                .map(LinkResponseDetailDto::new)
                .collect(Collectors.toList());
    }
}
