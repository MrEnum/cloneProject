package com.sparta.cloneproject.domain;

import com.sparta.cloneproject.dto.social.KakaoUserInfoDto;
import com.sparta.cloneproject.dto.user.request.SignupRequestDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "user_table")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String fullName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserConfirmEnum userConfirmEnum;

    @Column(unique = true)
    private Long kakaoId;


    public User(SignupRequestDto signupRequestDto, String password) {
        this.fullName = signupRequestDto.getFullName();
        this.userEmail = signupRequestDto.getUserEmail();
        this.userName = signupRequestDto.getUserName();
        this.password = password;
        this.userConfirmEnum = UserConfirmEnum.BEFORE_CONFIRM;
        this.kakaoId = null;
    }

    public User(KakaoUserInfoDto userInfoDto, String password) {
        this.fullName = null;
        this.userEmail = userInfoDto.getEmail();
        this.userName = userInfoDto.getNickname();
        this.password = password;
        this.kakaoId = userInfoDto.getId();
    }


}
