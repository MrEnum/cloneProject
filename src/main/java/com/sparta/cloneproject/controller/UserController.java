package com.sparta.cloneproject.controller;

import com.sparta.cloneproject.dto.user.request.UserInfoRequestDto;
import com.sparta.cloneproject.dto.user.response.LoginResponseDto;

import com.sparta.cloneproject.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    //    USER service DI
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public ResponseEntity<LoginResponseDto> registerUser(@Valid @RequestBody UserInfoRequestDto userInfoRequestDto) {
        return userService.registerUser(userInfoRequestDto);
    }



}
