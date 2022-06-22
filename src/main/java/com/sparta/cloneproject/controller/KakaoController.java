package com.sparta.cloneproject.controller;

import com.sparta.cloneproject.service.KakaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Controller
@RestController
public class KakaoController {

    private final KakaoService kakaoService;

    public KakaoController(KakaoService kakaoService){
        this.kakaoService = kakaoService;
    }
//    <이번 적용 방식>
//    클라이언트 -> 카카오 -> 서버 -> 카카오 -> 서버 -> 클라이언트

//    < 일반적인 방식 : 페이지 컨트롤은 일반 적으로 클라이언트의 몫이기 때문에
//    부득이한 경우를 제외 하면 클라이언트에서 서버로 요청을 보내는 루트로 설계하는 것이 바람직하다.>
//    클라이언트 -> 카카오 -> 클라이언트 -> 서버 -> 카카오 -> 서버 -> 클라이언트

//    366bf4df105f7d1fb0c91cb6b4faeba0  <== REST API 키
//    http://localhost:8080/user/signin/kakao  <== 콜백 주소
//    https://kauth.kakao.com/oauth/authorize?client_id=366bf4df105f7d1fb0c91cb6b4faeba0&redirect_uri=http://localhost:8080/user/signin/kakao&response_type=code
    @GetMapping("user/signin/kakao")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse servletResponse) throws IOException {
        kakaoService.kakaoLogin(code, servletResponse);
        return "redirect:http://lacalhost:8080/posts";  // 로그인 성공 시 처리 방법 협의 필요.
    }


    // 카카오 계정과 함께 로그아웃
    // https://kauth.kakao.com/oauth/logout?client_id=366bf4df105f7d1fb0c91cb6b4faeba0&logout_redirect_uri=http://localhost:8080/user/logout
    @GetMapping("user/logout")
    public String check(){
        return "redirect:http://lacalhost:8080/posts";
    }


}
