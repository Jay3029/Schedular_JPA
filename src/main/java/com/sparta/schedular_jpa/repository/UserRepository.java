package com.sparta.schedular_jpa.repository;

import com.sparta.schedular_jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Email로 유저 찾기
    Optional<User> findByEmail(String email);
}
