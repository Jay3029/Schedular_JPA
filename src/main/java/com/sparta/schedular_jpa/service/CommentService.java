package com.sparta.schedular_jpa.service;

import com.sparta.schedular_jpa.dto.CommentRequestDto;
import com.sparta.schedular_jpa.dto.CommentResponseDto;
import com.sparta.schedular_jpa.dto.ScheduleResponseDto;
import com.sparta.schedular_jpa.entity.Comment;
import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.repository.CommentRepository;
import com.sparta.schedular_jpa.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;



    // CREATE
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        // 해당 Comment가 달린 Schedule 확인
        Schedule schedule = scheduleRepository.findById(commentRequestDto.getSchedule_id()).orElseThrow(() -> new IllegalArgumentException("Invalid schedule ID"));

        // RequestDTO -> Entity
        Comment comment = new Comment(commentRequestDto);
        comment.setSchedule(schedule);

        // DB에 저장
        Comment savedComment = commentRepository.save(comment);

        // Entity -> ResponseDTO
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        return commentResponseDto;
    }

    // READ One Comment Service
    public CommentResponseDto getComment(Long id) {
        Comment comment = findComment(id);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }
    // READ All Comment Service
    public List<CommentResponseDto> getAllComments() {
        // DB 전체 조회
        return commentRepository.findAll().stream().map(CommentResponseDto::new).toList();
    }



    // UPDATE Comment
    @Transactional
    public void updateComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(id);
        comment.update(commentRequestDto);
    }



    // DELETE Comment
    public void deleteComment(Long id) {
        Comment comment = findComment(id);
        commentRepository.delete(comment);
    }



    // ID에 해당하는 Comment의 존재 유무 확인
    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Is not Exist Schedule.")
        );
    }
}
