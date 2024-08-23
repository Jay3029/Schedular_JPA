package com.sparta.schedular_jpa.entity;

import com.sparta.schedular_jpa.dto.CommentRequestDto;
import com.sparta.schedular_jpa.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;
    @Column(name = "created_date", nullable = false, insertable = false, updatable = false)
    private Timestamp createdDate;
    @Column(name = "modified_date", nullable = false, insertable = false)
    private Timestamp modifiedDate;

    // 하나의 일정에 복수의 댓글이 연관, 일대다 연관관계 설정
    // 외래키의 주인
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


    public Comment(CommentRequestDto commentRequestDto) {
        this.username = commentRequestDto.getUsername();
        this.contents = commentRequestDto.getContents();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.username = commentRequestDto.getUsername();
        this.contents = commentRequestDto.getContents();
    }

}
