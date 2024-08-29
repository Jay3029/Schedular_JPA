package com.sparta.schedular_jpa.dto.scheduleDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.schedular_jpa.dto.userDto.AssignedUsersResponseDto;
import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponseDto {

    private Long id;
    private List<Long> user;
    private String weather;
    private String title;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String numOfComments;
    private List<AssignedUsersResponseDto> assignedUsers;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.user = schedule.getUsers().stream().map(User::getId).toList();
        this.weather = schedule.getWeather();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdDate = schedule.getCreatedDate();
        this.modifiedDate = schedule.getModifiedDate();
        this.numOfComments = "총 댓글 "+schedule.getComments().size();
    }


}
