package com.sparta.cloneproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.cloneproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class pageController {
    private final UserService userService;

    //    USER service DI
    @Autowired
    public pageController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/page")
    public void kakaoLogin(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "https://naver.com");
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/confirm-email")
    public String viewConfirmEmail(@Valid @RequestParam String token) {
        userService.confirmEmail(token);
        return "redirect:/page";
    }
}
