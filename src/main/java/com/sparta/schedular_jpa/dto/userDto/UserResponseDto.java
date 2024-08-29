package com.sparta.schedular_jpa.dto.userDto;

import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<Long> schedules;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdDate = user.getCreatedDate();
        this.modifiedDate = user.getModifiedDate();
        this.schedules = user.getSchedules().stream().map(Schedule::getId).toList();
    }
}
