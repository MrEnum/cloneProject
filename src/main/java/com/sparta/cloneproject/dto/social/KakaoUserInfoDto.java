package com.sparta.cloneproject.dto.social;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfoDto {

    private Long id;
    private String nickname;
    private String email;
}
