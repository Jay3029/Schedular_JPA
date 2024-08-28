package com.sparta.schedular_jpa.controller;

import com.sparta.schedular_jpa.dto.signinDto.SignInRequestDto;
import com.sparta.schedular_jpa.dto.signinDto.SignInResponseDto;
import com.sparta.schedular_jpa.dto.signupDto.SignUpRequestDto;
import com.sparta.schedular_jpa.dto.signupDto.SignUpResponseDto;
import com.sparta.schedular_jpa.dto.userDto.UserRequestDto;
import com.sparta.schedular_jpa.dto.userDto.UserResponseDto;
import com.sparta.schedular_jpa.dto.userscheduleDto.UserScheduleRequestDto;
import com.sparta.schedular_jpa.dto.userscheduleDto.UserScheduleResponseDto;
import com.sparta.schedular_jpa.jwt.JwtUtil;
import com.sparta.schedular_jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // POST 회원가입
    @PostMapping("/auth/sign-up")
    public ResponseEntity<SignUpResponseDto> signUpUser(@RequestBody SignUpRequestDto signUpRequestDto) {
        System.out.println(signUpRequestDto);
        SignUpResponseDto signUpResponseDto = userService.signUpUser(signUpRequestDto);
        return ResponseEntity.ok().body(signUpResponseDto);
    }

    // POST 로그인
    @PostMapping("/auth/sign-in")
    public ResponseEntity<SignInResponseDto> signInUser(@RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponseDto = userService.signInUser(signInRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", signInResponseDto.getToken());
        return ResponseEntity.ok().headers(httpHeaders).body(signInResponseDto);
    }

    // GET 토큰 확인
    @GetMapping("/auth")


    /*----------------------------------------------------------------------------------------------------------------*/


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
    @PostMapping("/users/assign-users")
    public ResponseEntity<UserScheduleResponseDto> assignUsers(@RequestBody UserScheduleRequestDto userScheduleRequestDto) {
        UserScheduleResponseDto userScheduleResponseDto = userService.assignNewUsers(userScheduleRequestDto);

        return ResponseEntity.ok().body(userScheduleResponseDto);
    }
}
