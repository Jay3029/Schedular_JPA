package com.sparta.schedular_jpa.dto.userDto;

import lombok.Getter;
import java.sql.Timestamp;

@Getter
public class UserRequestDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String scheduleIdList;
}
