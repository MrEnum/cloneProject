package com.sparta.cloneproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.cloneproject.service.KakaoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoController {

    private final KakaoService kakaoService;

    public KakaoController(KakaoService kakaoService){
        this.kakaoService = kakaoService;
    }

//    366bf4df105f7d1fb0c91cb6b4faeba0  <== REST API 키
//    http://localhost:8080/user/signin/kakao  <== 콜백 주소
//    https://kauth.kakao.com/oauth/authorize?client_id=366bf4df105f7d1fb0c91cb6b4faeba0&redirect_uri=http://localhost:8080/user/signin/kakao&response_type=code   <== front End href 용
    @GetMapping("user/signin/kakao")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoService.kakaoLogin(code);
        return "redirect:http://lacalhost:3000/posts";  // 로그인 성공 시 처리 방법 협의 필요.
    }


    // 카카오 계정과 함께 로그아웃
    // https://kauth.kakao.com/oauth/logout?client_id=366bf4df105f7d1fb0c91cb6b4faeba0&logout_redirect_uri=http://localhost:8080/user/logout
    @GetMapping("user/logout")
    public String check(){
        return "redirect:http://lacalhost:3000/posts";
    }


}
