package com.example.demo.domain.category.service;

import com.example.demo.domain.category.domain.Category;
import com.example.demo.domain.category.repository.CategoryRepository;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserFacade userFacade;

    @Transactional
    public Long createCategory(Post post, String category) {
        User user = userFacade.getCurrentUser();

        Category ca = categoryRepository.save(
                Category.builder()
                        .name(category)
                        .build());

        ca.confirmPost(post);
        ca.confirmUser(user);

        System.out.println("category : " + ca.getId());
        return ca.getId();
    }
}
