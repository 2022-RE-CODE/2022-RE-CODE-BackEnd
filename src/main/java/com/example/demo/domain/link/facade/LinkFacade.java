package com.example.demo.domain.link.facade;

import com.example.demo.domain.link.domain.Link;
import com.example.demo.domain.link.domain.repository.LinkRepository;
import com.example.demo.domain.link.exception.AlreadyExistsLinkTitle;
import com.example.demo.domain.link.exception.NotFoundLink;
import com.example.demo.domain.link.presentation.dto.res.LinkResponseDto;
import com.example.demo.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */

@Component
@RequiredArgsConstructor
public class LinkFacade {

    private final LinkRepository linkRepository;
    private final UserFacade userFacade;

    public Link create(Link link) {
        if (linkRepository.existsByTitleAndUser(link.getTitle(), userFacade.getCurrentUser())) {
            throw AlreadyExistsLinkTitle.EXCEPTION;
        }
        return linkRepository.save(link);
    }

    public List<Link> getMyLinks() {
        return linkRepository.findByUser(userFacade.getCurrentUser());
    }

    public Link findByLinkId(Long linkId) {
        return linkRepository.findById(linkId)
                .orElseThrow(() -> NotFoundLink.EXCEPTION);
    }
}
