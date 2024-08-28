package com.sparta.schedular_jpa.dto.commentDto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CommentRequestDto {

    private String username;
    private String contents;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Long schedule_id;

}
