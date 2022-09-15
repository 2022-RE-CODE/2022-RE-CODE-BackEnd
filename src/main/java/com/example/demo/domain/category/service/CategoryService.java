package com.example.demo.domain.category.service;

import com.example.demo.domain.category.domain.Category;
import com.example.demo.domain.category.repository.CategoryRepository;
import com.example.demo.domain.category.web.dto.request.CategoryCreateRequestDto;
import com.example.demo.domain.post.domain.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.config.security.SecurityUtil;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createCategory(Post post, String category) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

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
