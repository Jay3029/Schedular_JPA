package com.sparta.schedular_jpa.entity;

import com.sparta.schedular_jpa.repository.UserRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_schedule")
@NoArgsConstructor
public class UserSchedule {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_schedule_id")
    private Long user_schedule_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;



    public UserSchedule(User user, Schedule schedule) {
        this.user = user;
        this.schedule = schedule;
    }

}
