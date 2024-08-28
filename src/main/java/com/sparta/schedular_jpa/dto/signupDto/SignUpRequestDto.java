package com.sparta.schedular_jpa.dto.signupDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    private String username;
    private String email;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
