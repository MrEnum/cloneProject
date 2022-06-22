package com.sparta.cloneproject.aop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    // customizing 예외 처리
    // PostApiException : 게시물 관련 예외처리
    // UserApiException : 유저 관련 예외처리
    @ExceptionHandler(value = {PostApiException.class, UserApiException.class})
    public ResponseEntity<Object> handleApiRequestException(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDto(false, e.getMessage()));
    }

    // Bean Validation 예외처리
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> validException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDto(false,e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

}

