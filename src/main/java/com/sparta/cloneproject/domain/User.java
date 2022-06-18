package com.sparta.cloneproject.domain;

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

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;


    public User(SignupRequestDto signupRequestDto, String password) {
        this.firstName = signupRequestDto.getFirstName();
        this.lastName = signupRequestDto.getLastName();
        this.userEmail = signupRequestDto.getUserEmail();
        this.userName = signupRequestDto.getUserName();
        this.password = password;
    }
}
