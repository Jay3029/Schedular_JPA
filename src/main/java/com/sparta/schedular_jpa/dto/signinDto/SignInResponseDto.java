package com.sparta.schedular_jpa.dto.signinDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponseDto {

    private String token;
    private String email;

    public SignInResponseDto(String token, String email) {
        this.token = token;
        this.email = email;
    }
}
