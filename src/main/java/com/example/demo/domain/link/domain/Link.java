package com.example.demo.domain.link.domain;

import com.example.demo.domain.user.User;
import com.example.demo.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author 최원용
 * @version 2.0.0
 * @Created by 최원용 on 2022. 12. 13.
 */

@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Link extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Link(String title, String url) {
        this.title = title;
        this.url = url;
    }

    // 연관관계 편의 메소드
    public void confirmUser(User user) {
        this.user = user;
        user.addLink(this);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateUrl(String url) {
        this.url = url;
    }
}
