package com.sparta.schedular_jpa.dto.userscheduleDto;

import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import com.sparta.schedular_jpa.entity.UserSchedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserScheduleResponseDto {

    private User user;
    private Schedule schedule;

    public UserScheduleResponseDto(UserSchedule userSchedule) {
        this.user = userSchedule.getUser();
        this.schedule = userSchedule.getSchedule();
    }

}
