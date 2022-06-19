package com.sparta.cloneproject.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String description;
    private String imageUrl;
    private String fileName;

    public void setImageUrlAndFileName(FileRequestDto fileRequestDto) {
        this.imageUrl = fileRequestDto.getImageUrl();
        this.fileName = fileRequestDto.getFileName();
    }
    public void setImageUrlAndFileName(String imageUrl, String fileName) {
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }
}
