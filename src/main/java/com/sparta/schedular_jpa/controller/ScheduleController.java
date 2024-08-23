package com.sparta.schedular_jpa.controller;

import com.sparta.schedular_jpa.dto.ScheduleRequestDto;
import com.sparta.schedular_jpa.dto.ScheduleResponseDto;
import com.sparta.schedular_jpa.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    // JPA Repository Interface 선언
    private final ScheduleService scheduleService;



    // CREATE
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {

        return scheduleService.createSchedule(scheduleRequestDto);

    }



    // READ (단일, ID값으로 조회)
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.getSchedule(id);

        if (scheduleResponseDto != null) {
            return ResponseEntity.ok().body(scheduleResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    // READ (전체, 수정일과 작성자로 조회)
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return scheduleService.getSchedules(pageNo, pageSize);

    }



    // UPDATE (비밀번호 요구)
    @PutMapping("/schedules/{id}")
    public void updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {

        scheduleService.updateSchedule(id, scheduleRequestDto);

    }



    // DELETE (비밀번호 요구)
    @DeleteMapping("/schedules/{id}")
    public void deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {

        scheduleService.deleteSchedule(id, scheduleRequestDto);

    }
}
