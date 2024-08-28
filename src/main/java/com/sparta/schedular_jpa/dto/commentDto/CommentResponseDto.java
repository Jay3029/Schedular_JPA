package com.sparta.schedular_jpa.dto.commentDto;

import com.sparta.schedular_jpa.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class CommentResponseDto {

    private Long id;
    private String username;
    private String contents;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.contents = comment.getContents();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }

}
