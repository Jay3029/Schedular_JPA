package com.sparta.schedular_jpa.service;

import com.sparta.schedular_jpa.dto.UserRequestDto;
import com.sparta.schedular_jpa.dto.UserResponseDto;
import com.sparta.schedular_jpa.dto.UserScheduleRequestDto;
import com.sparta.schedular_jpa.dto.UserScheduleResponseDto;
import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import com.sparta.schedular_jpa.entity.UserSchedule;
import com.sparta.schedular_jpa.repository.ScheduleRepository;
import com.sparta.schedular_jpa.repository.UserRepository;
import com.sparta.schedular_jpa.repository.UserScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;
    private final ScheduleRepository scheduleRepository;


    // CREATE
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = new User(userRequestDto);

        User savedUser = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }




    // READ one User
    public UserResponseDto getUser(Long id) {
        User user = findUser(id);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }
    // READ ALL
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }



    // UPDATE
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        // ID에 맞는 유저 조회
        User user = findUser(id);
        // 해당 유저의 정보 업데이트
        user.update(userRequestDto);
        // 반환값 설정
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }



    // DELETE
    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }



    // 추가 유저 등록
    public UserScheduleResponseDto assignNewUsers(UserScheduleRequestDto userScheduleRequestDto) {
        // 기존 유저가 존재하는지 확인
        Long userId = userScheduleRequestDto.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Is not Exist User."));

        // 기존 일정이 존재하는지 확인
        Long scheduleId = userScheduleRequestDto.getScheduleId();
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("Is not Exist Schedule."));

        // 일정에 추가 유저 등록
        UserSchedule userSchedule = new UserSchedule(user, schedule);
        userScheduleRepository.save(userSchedule);

        // 반환값 생성
        UserScheduleResponseDto userScheduleResponseDto = new UserScheduleResponseDto(userSchedule);

        return userScheduleResponseDto;
    }


    // ID에 해당하는 User의 존재 유무 확인
    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Is not Exist Schedule.")
        );
    }
}
