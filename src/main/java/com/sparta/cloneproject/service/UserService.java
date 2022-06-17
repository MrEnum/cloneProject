package com.sparta.cloneproject.service;

import com.sparta.cloneproject.domain.User;
import com.sparta.cloneproject.controller.dto.user.request.SignupRequestDto;
import com.sparta.cloneproject.controller.dto.user.response.SignupResponseDto;
import com.sparta.cloneproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public ResponseEntity<SignupResponseDto> registerUser(SignupRequestDto signupRequestDto) {
        SignupResponseDto signupResponseDto = new SignupResponseDto();
        if(userRepository.findByUserEmail(signupRequestDto.getUserEmail()).isPresent()) {
            signupResponseDto.setSuccess(false);
            signupResponseDto.setMessage("중복된 아이디가 포함되어 있습니다.");
            return ResponseEntity.badRequest().body(signupResponseDto);
        }
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        User user = new User(signupRequestDto, password);
        userRepository.save(user);


        signupResponseDto.setSuccess(true);
        signupResponseDto.setMessage("회원가입 성공");
        return ResponseEntity.ok().body(signupResponseDto);
    }
}
