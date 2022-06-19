package com.sparta.cloneproject.controller;

import com.sparta.cloneproject.dto.post.PostRequestDto;
import com.sparta.cloneproject.security.UserDetailsImpl;
import com.sparta.cloneproject.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //게시글 작성
    @PostMapping("/posts/post")
    public void createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestPart(value = "postDto") PostRequestDto postRequestDto,
                           @RequestPart(value = "file") MultipartFile file
                           ){
        Long user = userDetails.getUser().getId();
        postService.createPost(postRequestDto, user, file);
    }

    //게시글 전체 조회
    //게시글 디테일 조회

}
