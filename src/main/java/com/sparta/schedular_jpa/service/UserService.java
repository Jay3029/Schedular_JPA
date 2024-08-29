package com.sparta.schedular_jpa.service;

import com.sparta.schedular_jpa.config.UserPasswordEncoder;
import com.sparta.schedular_jpa.dto.signinDto.SignInRequestDto;
import com.sparta.schedular_jpa.dto.signinDto.SignInResponseDto;
import com.sparta.schedular_jpa.dto.signupDto.SignUpRequestDto;
import com.sparta.schedular_jpa.dto.signupDto.SignUpResponseDto;
import com.sparta.schedular_jpa.dto.userDto.UserRequestDto;
import com.sparta.schedular_jpa.dto.userDto.UserResponseDto;
import com.sparta.schedular_jpa.dto.userscheduleDto.UserScheduleRequestDto;
import com.sparta.schedular_jpa.dto.userscheduleDto.UserScheduleResponseDto;
import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import com.sparta.schedular_jpa.entity.UserRoleEnum;
import com.sparta.schedular_jpa.entity.UserSchedule;
import com.sparta.schedular_jpa.jwt.JwtUtil;
import com.sparta.schedular_jpa.repository.ScheduleRepository;
import com.sparta.schedular_jpa.repository.UserRepository;
import com.sparta.schedular_jpa.repository.UserScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserPasswordEncoder userPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    // Sign Up (회원 가입)
    public SignUpResponseDto signUpUser(SignUpRequestDto signUpRequestDto) {

        String username = signUpRequestDto.getUsername();
        String email = signUpRequestDto.getEmail();
        String password = userPasswordEncoder.encode(signUpRequestDto.getPassword());

        // 이메일 중복 확인
        Optional<User> checkUser = userRepository.findByEmail(email);
        if(checkUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        // 요청한 권한이 ADMIN인지 확인하고, 맞으면 ADMIN Key를 입력했는지 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(signUpRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(signUpRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("Admin token is invalid");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, email, password, role);
        userRepository.save(user);

        return new SignUpResponseDto(user);
    }

    // Sign In (로그인)
    public SignInResponseDto signInUser(SignInRequestDto signInRequestDto) {
        String email = signInRequestDto.getEmail();
        String password = signInRequestDto.getPassword();

        User user = userRepository.findByEmail(email).get();
        if(user != null && userPasswordEncoder.matches(password, user.getPassword())) {

            String token = jwtUtil.generateJwtToken(user, UserRoleEnum.USER);
            SignInResponseDto signInResponseDto = new SignInResponseDto(token, email);

            return signInResponseDto;
        } else {
            return null;
        }

    }


//    // CREATE
//    public UserResponseDto createUser(UserRequestDto userRequestDto) {
//        User user = new User(userRequestDto);
//
//        User savedUser = userRepository.save(user);
//
//        UserResponseDto userResponseDto = new UserResponseDto(user);
//        return userResponseDto;
//    }




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
    @Transactional
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
    @Transactional
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

        schedule.addUser(user);

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
