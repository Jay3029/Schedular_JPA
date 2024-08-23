package com.sparta.schedular_jpa.repository;

import com.sparta.schedular_jpa.entity.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {
}
