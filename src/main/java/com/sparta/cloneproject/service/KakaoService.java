package com.sparta.cloneproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.cloneproject.domain.User;
import com.sparta.cloneproject.dto.social.KakaoUserInfoDto;
import com.sparta.cloneproject.repository.UserRepository;
import com.sparta.cloneproject.security.UserDetailsImpl;
import com.sparta.cloneproject.security.jwt.JwtTokenUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Service
public class KakaoService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public KakaoService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void kakaoLogin(String code, HttpServletResponse servletResponse) throws IOException {

        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);
        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);
        // 3. kakaoID DB에 존재 여부 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoUserInfoDto.getId()).orElse(null);
        // 3-2. 카카오 아이디가 DB에 없으면 회원 가입하기
        if(kakaoUser == null){

            // 비밀번호 만들기
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            kakaoUser = new User(kakaoUserInfoDto, encodedPassword);

            // DB에 회원 정보 저장
            userRepository.save(kakaoUser);
        }

        // 4. 강제 로그인 처리   // 구조 이해하고 넘어 가기 by.민수
        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT토큰 헤더에 생성
        String token = JwtTokenUtils.generateJwtToken(userDetails);
        servletResponse.addHeader("Authorization", "Bearer " + token);

    }


    // AccessToken 발행 process < 1. "인가 코드"로 "액세스 토큰" 요청 >
    private String getAccessToken(String code) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "366bf4df105f7d1fb0c91cb6b4faeba0");   // Rest API Key
        body.add("redirect_uri", "http://localhost:8080/user/signin/kakao");  // 콜백 주소 ***** 배포시 수정 필요 *****
        body.add("code", code);  // 최초 카카오서버에 요청 하여 받은 코드

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",  //요청 주소(카카오 서버)
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();      // accessToken 반환
    }


    // user 정보 가져오기  < 2. 토큰으로 카카오 API 호출 >
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        // HTTP Header 생성
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        return new KakaoUserInfoDto(id, nickname, email);
    }


}

