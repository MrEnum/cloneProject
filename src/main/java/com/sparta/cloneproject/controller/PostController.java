package com.sparta.cloneproject.controller;

import com.sparta.cloneproject.dto.post.PostRequestDto;
import com.sparta.cloneproject.dto.post.PostResponseDto;
import com.sparta.cloneproject.repository.PostRepository;
import com.sparta.cloneproject.security.UserDetailsImpl;
import com.sparta.cloneproject.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }
    //게시글 작성
    @PostMapping("/posts/post")
    public void createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestPart(value = "postDto") PostRequestDto postRequestDto,
                           @RequestPart(value = "file") MultipartFile file//ARC부분에
                           ){
        Long user = userDetails.getUser().getId();
        postService.createPost(postRequestDto, user, file);
    }

    //게시글 전체 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getAllPosts(){
        return postService.getAllpost();
    }
    //게시글 디테일 조회

}
