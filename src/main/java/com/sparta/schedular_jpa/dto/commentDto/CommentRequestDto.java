package com.sparta.schedular_jpa.dto.commentDto;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {

    private String username;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long schedule_id;

}
