package com.sparta.cloneproject.dto.user.request;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignupRequestDto {

    String fullName;

    @Pattern(regexp = "^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "E-mail 형식이 아닙니다.")
    String userEmail;
//    닉네임
    String userName;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 8자 이상입니다.")
    String password;
}