package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.nickname like %:nickname%")
    List<User> findByNickname(@Param("nickname") String nickname);

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
