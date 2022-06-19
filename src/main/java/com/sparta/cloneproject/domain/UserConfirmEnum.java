package com.sparta.cloneproject.domain;

public enum UserConfirmEnum {
    BEFORE_CONFIRM(false),  // 사용자 권한
    OK_CONFIRM(true);  // 관리자 권한

    boolean code;

    UserConfirmEnum(boolean code) {
        this.code = code;
    }
}