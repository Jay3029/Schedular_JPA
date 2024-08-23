package com.sparta.schedular_jpa.dto;

import lombok.Getter;
import java.sql.Timestamp;

@Getter
public class UserRequestDto {
    private Long id;
    private String username;
    private String email;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String scheduleIdList;
}
