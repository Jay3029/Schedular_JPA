package com.sparta.schedular_jpa.dto.signinDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDto {

    private Long id;
    private String email;
    private String password;
}
