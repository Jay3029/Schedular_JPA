package com.sparta.schedular_jpa.entity;

import com.sparta.schedular_jpa.dto.scheduleDto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;
    @Column(name = "created_date", nullable = false, insertable = false, updatable = false)
    private Timestamp createdDate;
    @Column(name = "modified_date", nullable = false, insertable = false)
    private Timestamp modifiedDate;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "weather")
    private String weather;


    // 다대일 양방향 관계 설정, 영속성 전이 타입을 ALL로 하여 영속 상태 변경에 대해 함께 동작하도록 설정
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Comment> comments = new ArrayList<>();


    // 유저와 할일의 다대다 연관관계를 위한 OneToMany ManyToOne 연관관계 설정
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserSchedule> users_schedules = new ArrayList<>();


    public List<User> getAssignedUsers() {
        return users_schedules.stream().map(UserSchedule::getUser).toList();
    }


    public Schedule(ScheduleRequestDto requestDto) {
        this.user_id = requestDto.getUserid();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
