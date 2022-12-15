package com.example.demo.domain.category.service;

import com.example.demo.domain.category.domain.Category;
import com.example.demo.domain.category.domain.repository.CategoryRepository;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.post.web.dto.response.PostResponseDto;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserFacade userFacade;
    private final PostRepository postRepository;

    @Transactional
    public Long createCategory(Post post, String category) {
        User user = userFacade.getCurrentUser();

        Category ca = categoryRepository.save(
                Category.builder()
                        .name(category)
                        .build());

        ca.confirmPost(post);
        ca.confirmUser(user);

        return ca.getId();
    }

    public List<PostResponseDto> findByCategoryName(String categoryName) {
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post p : postRepository.findAll()) {
            p.getCategories()
                    .forEach(category -> {
                        if (Objects.equals(category.getName(), categoryName)) {
                            postResponseDtos.add(new PostResponseDto(p));
                        }
                    });
            }
        return postResponseDtos;
    }

}