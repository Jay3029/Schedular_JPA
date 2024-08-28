package com.sparta.schedular_jpa.controller;

import com.sparta.schedular_jpa.dto.commentDto.CommentRequestDto;
import com.sparta.schedular_jpa.dto.commentDto.CommentResponseDto;
import com.sparta.schedular_jpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // CREATE
    @PostMapping("/comments")
    public void createComment(@RequestBody CommentRequestDto commentRequestDto) {

        CommentResponseDto commentResponseDto = commentService.createComment(commentRequestDto);

        //return ResponseEntity.ok().body(commentResponseDto);

    }


    // READ (단일, ID값으로 조회)
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long id) {

        CommentResponseDto commentResponseDto = commentService.getComment(id);

        if (commentResponseDto != null) {
            return ResponseEntity.ok().body(commentResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    // READ (전체 조회)
    @GetMapping("/comments")
    public List<CommentResponseDto> getAllComments() {

        return commentService.getAllComments();

    }



    // UPDATE
    @PutMapping("/comments/{id}")
    public void updateSchedule(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {

        commentService.updateComment(id, commentRequestDto);

    }



    // DELETE (비밀번호 요구)
    @DeleteMapping("/comments/{id}")
    public void deleteSchedule(@PathVariable Long id) {

        commentService.deleteComment(id);

    }
}
