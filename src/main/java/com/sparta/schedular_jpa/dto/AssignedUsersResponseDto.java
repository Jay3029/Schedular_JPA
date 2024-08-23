package com.sparta.schedular_jpa.dto;

import com.sparta.schedular_jpa.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignedUsersResponseDto {
    private Long id;
    private String username;
    private String email;

    public AssignedUsersResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
