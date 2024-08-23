package com.sparta.schedular_jpa.entity;

import com.sparta.schedular_jpa.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email_address", nullable = false)
    private String email;
    @Column(name = "created_date", nullable = false, insertable = false, updatable = false)
    private Timestamp createdDate;
    @Column(name = "modified_date", nullable = false, insertable = false)
    private Timestamp modifiedDate;
    @Column(name = "schedule_id")
    private String scheduleIdList;
    @Column(name = "password")
    private String password;

    // 유저와 할일은 서로 다대다 연관관계를 가져야 한다.
    // 단, @ManyToMany는 사용을 지양한다.
    // 결과적으로 중간 table을 거쳐 OneToMany, ManyToOne의 연결을 하도록 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSchedule> users_schedules = new ArrayList<>();


    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
    }

    public void update(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
    }



    /*
    public List<Schedule> getAssignedSchedules() {
        return users_schedules.stream().map(UserSchedule::getSchedule).toList();
    }
    */
}
