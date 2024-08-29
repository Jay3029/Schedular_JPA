package com.sparta.schedular_jpa.dto.scheduleDto;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {

    private Long id;
    private Long userid;
    private String title;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}

