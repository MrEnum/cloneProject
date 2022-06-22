package com.sparta.cloneproject.dto.user.request;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignupRequestDto {

    String fullName;

//     @Email은 "" < 공백 허용됨
//    @Email
    @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "E-mail 형식이 아닙니다.")
    String userEmail;
//    닉네임
    String userName;

    @Size(min = 8, message = "최소 8자 이상 작성해주세요")
    String password;

}