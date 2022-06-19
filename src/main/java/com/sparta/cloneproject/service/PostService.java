//package com.sparta.cloneproject.service;
//
//import com.sparta.cloneproject.domain.Post;
//import com.sparta.cloneproject.domain.User;
//import com.sparta.cloneproject.dto.post.PostRequestDto;
//import com.sparta.cloneproject.repository.PostRepository;
//import com.sparta.cloneproject.repository.UserRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class PostService {
//
//    private final S3Service s3Service;
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//
//    public PostService(S3Service s3Service, PostRepository postRepository, UserRepository userRepository) {
//        this.s3Service = s3Service;
//        this.postRepository = postRepository;
//        this.userRepository = userRepository;
//    }
//
//    public void createPost(PostRequestDto postRequestDto, Long userId, MultipartFile file) {
//        User user = userRepository.findById(userId).orElseThrow(
//                () -> new IllegalArgumentException("등록되지 않은 사용자입니다.")
//        );
//
//        if (file.isEmpty()) {
//            postRequestDto.setImageUrl(null);
//
//        } else {
//            postRequestDto.setImageUrlAndFileName(s3Service.upload(file));
//        }
//        Post post = postRepository.save(new Post(postRequestDto, user));
//
//
//    }
//}