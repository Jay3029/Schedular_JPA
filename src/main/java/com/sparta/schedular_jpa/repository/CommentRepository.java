package com.sparta.schedular_jpa.repository;

import com.sparta.schedular_jpa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
