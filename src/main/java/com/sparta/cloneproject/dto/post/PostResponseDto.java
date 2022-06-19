package com.sparta.cloneproject.dto.post;

import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postID;
    private boolean success;
    private String imageUrl;
    private String description;
    ///댓글///////////////////////////

}
