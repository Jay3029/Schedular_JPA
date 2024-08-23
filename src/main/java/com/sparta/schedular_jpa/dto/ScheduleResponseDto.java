package com.sparta.schedular_jpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponseDto {

    private Long id;
    private Long userid;
    private String title;
    private String contents;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String numOfComments;
    private List<AssignedUsersResponseDto> assignedUsers;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.userid = schedule.getUser_id();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdDate = schedule.getCreatedDate();
        this.modifiedDate = schedule.getModifiedDate();
        this.numOfComments = "총 댓글 "+schedule.getComments().size();
    }


}
