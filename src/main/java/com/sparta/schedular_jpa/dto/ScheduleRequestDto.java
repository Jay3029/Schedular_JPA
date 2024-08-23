package com.sparta.schedular_jpa.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ScheduleRequestDto {

    private Long id;
    private Long userid;
    private String title;
    private String contents;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

}

