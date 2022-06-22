package com.sparta.cloneproject.dto.post;

import com.sparta.cloneproject.domain.Post;
import lombok.Data;

@Data
public class FolderResponseDto {

    private Long postID;
    private String imageUrl;
    private String description;
    private String folder;


    public FolderResponseDto(Post post, String folder) {
        this.postID = post.getId();
        this.imageUrl = post.getImageUrl();
        this.description = post.getDescription();
        this.folder = folder;
    }

}
