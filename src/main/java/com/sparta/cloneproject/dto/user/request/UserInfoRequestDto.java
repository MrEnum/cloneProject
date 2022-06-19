package com.sparta.cloneproject.dto.user.request;

import lombok.Data;

import javax.validation.Valid;


@Data
public class UserInfoRequestDto {
    @Valid
    SignupRequestDto userInfo;
}
