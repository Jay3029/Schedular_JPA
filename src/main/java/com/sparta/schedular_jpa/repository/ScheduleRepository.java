package com.sparta.schedular_jpa.repository;

import com.sparta.schedular_jpa.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


// @Repository는 이미 구현체를 통해 생성한 것에 포함됨
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
}
