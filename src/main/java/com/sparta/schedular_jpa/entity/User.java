package com.sparta.schedular_jpa.entity;

import com.sparta.schedular_jpa.dto.signupDto.SignUpRequestDto;
import com.sparta.schedular_jpa.dto.userDto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email_address", nullable = false)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    // 유저와 할일은 서로 다대다 연관관계를 가져야 한다.
    // 단, @ManyToMany는 사용을 지양한다.
    // 결과적으로 중간 table을 거쳐 OneToMany, ManyToOne의 연결을 하도록 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSchedule> userSchedules = new ArrayList<>();


    public User(UserRequestDto userRequestDto) {
        this.username = userRequestDto.getUsername();
        this.email = userRequestDto.getEmail();
    }

    public User(String username, String email, String password, UserRoleEnum role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void update(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
    }

    // 스케줄을 유저에 추가
    public void addSchedule(Schedule schedule) {
        UserSchedule userSchedule = new UserSchedule(this, schedule);
        this.userSchedules.add(userSchedule);
        schedule.getUserSchedules().add(userSchedule);
    }

    // 스케줄을 유저에서 제거
    public void removeSchedule(Schedule schedule) {
        userSchedules.removeIf(userSchedule -> userSchedule.getSchedule().equals(schedule));
        schedule.getUserSchedules().removeIf(userSchedule -> userSchedule.getUser().equals(this));
    }

    public List<Schedule> getSchedules() {
        return this.userSchedules.stream()
                .map(UserSchedule::getSchedule)
                .collect(Collectors.toList());
    }

}
