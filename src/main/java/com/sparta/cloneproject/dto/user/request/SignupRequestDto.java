package com.sparta.cloneproject.dto.user.request;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class SignupRequestDto {
    String firstName;
    String lastName;

    @Email
    String userEmail;
//    닉네임
    @Pattern(regexp =  "^[a-zA-Z\\d._]$")
    String userName;

    String password;

}
