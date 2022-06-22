package com.sparta.cloneproject.controller;

import com.sparta.cloneproject.domain.Post;
import com.sparta.cloneproject.dto.post.*;
import com.sparta.cloneproject.repository.PostRepository;
import com.sparta.cloneproject.security.UserDetailsImpl;
import com.sparta.cloneproject.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
    ) {
        Long user = userDetails.getUser().getId();
        postService.createPost(postRequestDto, user, file);
    }

    //게시글 전체 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllpost();
    }


    // 무한스크롤 적용 게시글 전체 조회
    @GetMapping("/posts/pagination")
    public ResponseEntity<List<PostResponseDto>> getPostsPages(@RequestBody PagesRequestDto requestDto) {
        return ResponseEntity.ok().body(postService.getPostsPages(requestDto));
    }


    //게시글 폴더 설정
    @PostMapping("/posts/{postId}/folder")
    public void createFolder(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @RequestBody FolderRequestDto requestDto){
        postService.createFolder(userDetails.getUser().getId(), postId, requestDto);
    }


    // 내가 가지고 있는 특정 폴더 전체 조회
    @GetMapping("/posts/{folder}/mine")
    public List<FolderResponseDto> getFolderPosts(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String folder){
        return postService.getFolderPosts(userDetails.getUser().getId(), folder);
    }


    //게시글 디테일 조회
    @GetMapping("/posts/{postId}")
    public Optional<Post> getPost(@PathVariable Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post;
    }

    //게시글 수정
    @PutMapping("/post/{postId}")
    public void updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable Long postId,
                                    @RequestPart(value = "postDto") PostRequestDto postRequestDto,
                                    @RequestPart(value = "file") MultipartFile file) {
        postService.updatePost(postId,userDetails.getUser().getId(), postRequestDto, file);

    }

    //게시글 삭제
    @DeleteMapping("/post/{postId}")
    public void deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @PathVariable Long postId) {
        postService.deletePost(postId, userDetails.getUser().getId());
    }
}
