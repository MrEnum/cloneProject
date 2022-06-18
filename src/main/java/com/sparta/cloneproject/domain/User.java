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
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private Long kakaoId;


    public User(SignupRequestDto signupRequestDto, String password) {
        this.firstName = signupRequestDto.getFirstName();
        this.lastName = signupRequestDto.getLastName();
        this.userEmail = signupRequestDto.getUserEmail();
        this.userName = signupRequestDto.getUserName();
        this.password = password;
        this.kakaoId = null;
    }

    public User(KakaoUserInfoDto userInfoDto, String password) {
        this.firstName = null;
        this.lastName = null;
        this.userEmail = userInfoDto.getEmail();
        this.userName = userInfoDto.getNickname();
        this.password = password;
        this.kakaoId = userInfoDto.getId();
    }


}
