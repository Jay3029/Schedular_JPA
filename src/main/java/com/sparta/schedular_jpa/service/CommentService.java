package com.sparta.schedular_jpa.service;

import com.sparta.schedular_jpa.dto.commentDto.CommentRequestDto;
import com.sparta.schedular_jpa.dto.commentDto.CommentResponseDto;
import com.sparta.schedular_jpa.entity.Comment;
import com.sparta.schedular_jpa.entity.Schedule;
import com.sparta.schedular_jpa.entity.User;
import com.sparta.schedular_jpa.jwt.JwtUtil;
import com.sparta.schedular_jpa.repository.CommentRepository;
import com.sparta.schedular_jpa.repository.ScheduleRepository;
import com.sparta.schedular_jpa.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


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

    // READ Some Schedule's Comments Service
    public List<CommentResponseDto> getCommentsOfSchedule(Long schedule_id) {
        List<Comment> comments = commentRepository.findByScheduleId(schedule_id);
        if(comments.isEmpty()) {
            throw new IllegalArgumentException("There is no comments found for schedule ID: " + schedule_id);
        }

        // commentRepo -> comment -> schedule -> schedule_id
        return comments.stream().map(CommentResponseDto::new).toList();
    }



    // UPDATE Comment
    @Transactional
    public void updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        User user = jwtUtil.getUserFromToken(request, userRepository);
        Comment comment = findComment(id);
        Schedule schedule = comment.getSchedule();

        List<Schedule> scheduleList = user.getSchedules();
        if(!scheduleList.contains(schedule)) {
            throw new RuntimeException("Is not your comment");
        }

        comment.update(commentRequestDto);
    }



    // DELETE Comment
    @Transactional
    public void deleteComment(Long id, HttpServletRequest request) {
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
