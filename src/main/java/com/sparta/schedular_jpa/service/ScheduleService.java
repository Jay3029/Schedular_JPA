package com.sparta.schedular_jpa.service;


import com.sparta.schedular_jpa.dto.AssignedUsersResponseDto;
import com.sparta.schedular_jpa.dto.CommentResponseDto;
import com.sparta.schedular_jpa.dto.ScheduleRequestDto;
import com.sparta.schedular_jpa.dto.ScheduleResponseDto;
import com.sparta.schedular_jpa.entity.Comment;
import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import com.sparta.schedular_jpa.entity.UserSchedule;
import com.sparta.schedular_jpa.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;



    // CREATE Schedule Service
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        // RequestDTO -> Entity
        Schedule schedule = new Schedule(scheduleRequestDto);

        // DB에 저장
        scheduleRepository.save(schedule);

        // Entity -> ResponseDTO
        ScheduleResponseDto scheduleResponseDTO = new ScheduleResponseDto(schedule);

        return scheduleResponseDTO;
    }


    // READ One Schedule Service
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = findSchedule(id);
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
        scheduleResponseDto.setAssignedUsers(schedule.getAssignedUsers().stream()
                .map(AssignedUsersResponseDto::new).toList());

        // 단일 하나 조회
        return scheduleResponseDto;
    }
    // READ All Schedule Service
    public List<ScheduleResponseDto> getSchedules(int pageNo, int pageSize) {
        // DB 전체 조회
        // Page를 통해 일정 부분만 조정하여 조회 (페이지 번호, 보여줄 데이터 양, 정렬 방식)
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("modifiedDate").descending());
        return scheduleRepository.findAll(pageable).stream().map(ScheduleResponseDto::new).toList();
    }


    // UPDATE Schedule Service
    @Transactional
    public void updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        // Optional, 해당 작성자의 일정이 존재하는지 확인
        Schedule schedule = findSchedule(id);
        schedule.update(scheduleRequestDto);
    }


    // DELETE Schedule Service
    public void deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        // 해당 작성자의 일정이 존재하는지 확인
        Schedule schedule = findSchedule(id);
        scheduleRepository.delete(schedule);
    }


    // ID에 해당하는 Schedule의 존재 유무 확인
    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Is not Exist Schedule.")
        );
    }

}