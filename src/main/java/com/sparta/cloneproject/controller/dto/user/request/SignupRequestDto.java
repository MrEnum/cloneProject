package com.sparta.cloneproject.controller.dto.user.request;

import lombok.Data;

@Data
public class SignupRequestDto {
    String firstName;
    String lastName;
    String userEmail;
//    닉네임
    String userName;
    String password;

}