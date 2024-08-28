package com.sparta.schedular_jpa.repository;

import com.sparta.schedular_jpa.entity.Comment;
import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByScheduleId(Long scheduleId);
    // List<Comment> findBySchedule(Schedule schedule);
    // -> 이렇게 작성하게 되면 이걸 Schedule_ID로 변환해서 Query문을 날림

}
