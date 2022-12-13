package com.example.demo.domain.link.service;

import com.example.demo.domain.link.domain.Link;
import com.example.demo.domain.link.facade.LinkFacade;
import com.example.demo.domain.link.presentation.dto.req.LinkCreateRequestDto;
import com.example.demo.domain.link.presentation.dto.res.LinkResponseDto;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<LinkResponseDto> getMyLinks() {
        return linkFacade.getMyLinks()
                .stream()
                .map(LinkResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateLink(Long linkId, LinkCreateRequestDto req) {
        Link link = linkFacade.findByLinkId(linkId);
        link.updateInfo(req);
    }
}
