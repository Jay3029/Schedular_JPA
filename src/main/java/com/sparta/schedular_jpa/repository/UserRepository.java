package com.sparta.schedular_jpa.repository;

import com.sparta.schedular_jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
