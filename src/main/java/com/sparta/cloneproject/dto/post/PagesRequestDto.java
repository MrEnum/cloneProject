package com.sparta.cloneproject.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PagesRequestDto {

    private Long lastPostId; // 가장 아래의 게시글 번호
    private int size; // 한 페이지당 게시글 수

}
