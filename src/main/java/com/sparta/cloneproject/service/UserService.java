package com.sparta.cloneproject.service;

import com.sparta.cloneproject.aop.exception.UserApiException;
import com.sparta.cloneproject.domain.ConfirmationToken;
import com.sparta.cloneproject.domain.User;
import com.sparta.cloneproject.domain.UserConfirmEnum;
import com.sparta.cloneproject.dto.user.request.SignupRequestDto;
import com.sparta.cloneproject.dto.user.request.UserInfoRequestDto;
import com.sparta.cloneproject.dto.user.response.LoginResponseDto;
import com.sparta.cloneproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> registerUser(UserInfoRequestDto userInfoRequestDto) {
        LoginResponseDto signupResponseDto = new LoginResponseDto();
        SignupRequestDto signupRequestDto = userInfoRequestDto.getUserInfo();

//      ID 중복 체크
        if (userRepository.findByUserEmail(signupRequestDto.getUserEmail()).isPresent()) {
            throw new UserApiException("중복된 아이디가 포함되어 있습니다.");
        }
//      PW Hash
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        User user = new User(signupRequestDto, password);

//      Email 전송
        confirmationTokenService.createEmailConfirmationToken(signupRequestDto.getUserEmail());
//      DB 저장
        userRepository.save(user);


        signupResponseDto.setSuccess(true);
        signupResponseDto.setMessage("회원가입 성공");
        return ResponseEntity.ok().body(signupResponseDto);
    }

    @Transactional
    public void confirmEmail(String token) {
        ConfirmationToken findConfirmationToken = confirmationTokenService.findByIdAndExpirationDateAfterAndExpired(token);
        Optional<User> findUserInfo = userRepository.findByUserEmail(findConfirmationToken.getUserEmail());
        findConfirmationToken.useToken();    // 토큰 만료

        if (!findUserInfo.isPresent()) {
            throw new UserApiException("잘못된 토큰값");
        }
//      User Confirm 정보 'OK' 로 변경
        findUserInfo.get().setUserConfirmEnum(UserConfirmEnum.OK_CONFIRM);
    }
}
