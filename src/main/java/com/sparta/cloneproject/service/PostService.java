package com.sparta.cloneproject.service;

import com.sparta.cloneproject.aop.exception.PostApiException;
import com.sparta.cloneproject.domain.Post;
import com.sparta.cloneproject.domain.User;
import com.sparta.cloneproject.dto.post.PostRequestDto;
import com.sparta.cloneproject.dto.post.PostResponseDto;
import com.sparta.cloneproject.repository.PostRepository;
import com.sparta.cloneproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final S3Service s3Service;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(S3Service s3Service, PostRepository postRepository, UserRepository userRepository) {
        this.s3Service = s3Service;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    //게시글 작성
    public void createPost(PostRequestDto postRequestDto, Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new PostApiException("요청하신 작업이 실패했습니다")
        );

        if (file.isEmpty()) {
            postRequestDto.setImageUrl(null);

        } else {
            postRequestDto.setImageUrlAndFileName(s3Service.upload(file));
        }
        Post post = postRepository.save(new Post(postRequestDto, user));
    }

    //게시글 전체 조회
    public List<PostResponseDto> getAllpost(){
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post  :posts) {
            String imageUrl = post.getImageUrl();
            String description = post.getDescription();

            PostResponseDto postResponseDto = new PostResponseDto(imageUrl,description, post.getId());
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;

    }
    //게시글 카테고리별 조회

    //게시글 삭제
    public void deletePost(Long postId, Long userId){
        Post post = postRepository.findByIdAndUserId(postId, userId);
        if(post == null ){
            throw new IllegalArgumentException("해당 글이 존재 하지 않거나 권한이 없습니다.");
        }
        //이미지 유알엘 삭제
        s3Service.deleteImageUrl((post.getImageUrl()));

        //게시글 삭제 시 댓글 삭제
        //게시글 삭제
        postRepository.deleteById(postId);
    }


    //게시글 수정

    public void updatePost(Long postId, Long userId, PostRequestDto postRequestDto, MultipartFile file) {
        Post post = postRepository.findByIdAndUserId(postId,userId);    //userId값과 ,postId값을 찾아라.
        if(post == null){                                               //없으면 throw 해라
            throw new NullPointerException("존재하지 않는 글입니다.");
        }
        //새 파일 등록
        if(!file.isEmpty()){    //받아오는 값에 파일이 없지 않다면(있다면)
            s3Service.deleteImageUrl(post.getFileName());   //기존 url삭제해라
            postRequestDto.setImageUrlAndFileName(s3Service.upload(file)); //그리고 파일을 다시 붙여라
        }else{  //아니면
            postRequestDto.setImageUrlAndFileName(post.getImageUrl(), post.getFileName());  //
        }
        //DB업데이트
        post.update(postRequestDto);
        postRepository.save(post);

    }
}