package com.sparta.schedular_jpa.dto.signupDto;

import com.sparta.schedular_jpa.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {

    private User user;

    public SignUpResponseDto( User user) {

        this.user = user;
    }
}
