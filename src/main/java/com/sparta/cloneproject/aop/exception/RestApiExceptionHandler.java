package com.sparta.cloneproject.aop.exception;

import com.sparta.cloneproject.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { PostApiException.class })
    public ResponseEntity<Object> handleApiRequestException(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDto(false, e.getMessage()));
    }

}

