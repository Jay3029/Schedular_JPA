package com.sparta.schedular_jpa.controller;

import com.sparta.schedular_jpa.dto.UserRequestDto;
import com.sparta.schedular_jpa.dto.UserResponseDto;
import com.sparta.schedular_jpa.dto.UserScheduleRequestDto;
import com.sparta.schedular_jpa.dto.UserScheduleResponseDto;
import com.sparta.schedular_jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //CREATE (유저 저장)
    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity.ok().body(userResponseDto);
    }



    // READ (단일 유저 조회)
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getUser(id);
        return ResponseEntity.ok().body(userResponseDto);
    }
    // READ (전체 유저 조회)
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }



    // UPDATE
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto userRequestDto) {

        UserResponseDto userResponseDto = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok().body(userResponseDto);
    }



    // DELETE
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().body("Delete User Successful");
    }



    // CREATE (유저 추가 등록)
    @PostMapping("/users/assignUsers")
    public void assignUsers(@RequestBody UserScheduleRequestDto userScheduleRequestDto) {
        UserScheduleResponseDto userScheduleResponseDto = userService.assignNewUsers(userScheduleRequestDto);

    }
}
