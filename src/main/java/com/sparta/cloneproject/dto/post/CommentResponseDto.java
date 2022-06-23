package com.sparta.cloneproject.dto.post;

import lombok.Data;

@Data
public class CommentResponseDto {
    private String content;

    public CommentResponseDto(String content){
        this.content = content;

    }
}
