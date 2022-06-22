package com.sparta.cloneproject.service;

import com.sparta.cloneproject.aop.exception.PostApiException;
import com.sparta.cloneproject.domain.Folder;
import com.sparta.cloneproject.domain.Post;
import com.sparta.cloneproject.domain.User;
import com.sparta.cloneproject.dto.post.*;
import com.sparta.cloneproject.repository.FolderRepository;
import com.sparta.cloneproject.repository.Mapping.PostMapping;
import com.sparta.cloneproject.repository.PostRepository;
import com.sparta.cloneproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final S3Service s3Service;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;


    @Autowired
    public PostService(S3Service s3Service, PostRepository postRepository, UserRepository userRepository, FolderRepository folderRepository) {
        this.s3Service = s3Service;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
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


    // 폴더 생성 (생성시 지정한 게시글 즉시 추가)
    public void createFolder(Long userId, Long postId, FolderRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new PostApiException("권한이 없습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostApiException("없는 게시물 입니다."));

        // 폴더명 중복 확인
        Boolean isSameFolderName = folderRepository.existsByUserIdAndFolder(userId, requestDto.getFolder());
        if(isSameFolderName){
            throw new PostApiException("폴더명이 존재 합니다.");
        }

        Folder folder = new Folder(requestDto.getFolder(), post, user);
        folderRepository.save(folder);  // DB에 새로운 폴더 저장

    }


    // 내가 가지고 있는 특정 폴더 전체 조회
    public List<FolderResponseDto> getFolderPosts(Long userId, String folderName) {
        // 유저 아이디와 폴더명 2개 모두 일치하는 게시물만 불러오기
        List<PostMapping> posts = folderRepository.findAllByUserIdAndFolder(userId, folderName);
        List<FolderResponseDto> responseDtos = new ArrayList<>();
        for(PostMapping post : posts){
            // 응답용 DTO에 제 배치
            responseDtos.add(new FolderResponseDto(post.getPost(), folderName));
        }
        return responseDtos;
    }


    // Infinite Scrolling Pagination
    public List<PostResponseDto> getPostsPages(PagesRequestDto requestDto) {
        // 불러올 페이지 조건 설정
        PageRequest pageRequest = PageRequest.of(0, requestDto.getSize(), Sort.by("modifiedAt").descending());
        // 조건에 맞춰 DB에서 불러오기
        List<Post> posts = postRepository.findByIdLessThanOrderByIdDesc(requestDto.getLastPostId(), pageRequest).getContent();

        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : posts){
            // 응답용 DTO에 제 배치
            postResponseDtos.add(new PostResponseDto(post.getImageUrl(), post.getDescription(), post.getId()));
        }

        return postResponseDtos;
    }


}