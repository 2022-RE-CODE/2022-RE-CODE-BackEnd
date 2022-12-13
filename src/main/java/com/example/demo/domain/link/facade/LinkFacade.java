package com.example.demo.domain.link.facade;

import com.example.demo.domain.link.domain.Link;
import com.example.demo.domain.link.domain.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */

@Component
@RequiredArgsConstructor
public class LinkFacade {

    private final LinkRepository linkRepository;

    public Link create(Link link) {
        return linkRepository.save(link);
    }
}
