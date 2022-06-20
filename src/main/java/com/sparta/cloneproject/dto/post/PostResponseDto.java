package com.sparta.cloneproject.dto.post;

import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postID;
//    private boolean success;
    private String imageUrl;
    private String description;

    public PostResponseDto(String imageUrl, String description, Long postID) {
        this.postID = postID;
        this.imageUrl = imageUrl;
        this.description = description;

    }
    ///댓글///////////////////////////

}
