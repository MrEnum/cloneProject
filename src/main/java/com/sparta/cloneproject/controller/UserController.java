package com.sparta.cloneproject.controller;

import com.sparta.cloneproject.controller.dto.user.request.SignupRequestDto;
import com.sparta.cloneproject.controller.dto.user.response.SignupResponseDto;
import com.sparta.cloneproject.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class UserController {

    private final UserService userService;

//    USER DI
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponseDto> registerUser(@RequestBody SignupRequestDto signupRequestDto) {

        return userService.registerUser(signupRequestDto);
    }

}
