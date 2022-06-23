package com.sparta.cloneproject.service;

import com.sparta.cloneproject.domain.Comment;
import com.sparta.cloneproject.domain.Post;
import com.sparta.cloneproject.domain.User;
import com.sparta.cloneproject.dto.post.CommentRequestDto;
import com.sparta.cloneproject.dto.post.CommentResponseDto;
import com.sparta.cloneproject.repository.CommentRepository;
import com.sparta.cloneproject.repository.PostRepository;
import com.sparta.cloneproject.repository.UserRepository;
import com.sparta.cloneproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //댓글 생성
    public void createComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user =userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                ()-> new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        Comment comment = new Comment(post, commentRequestDto, user);
        commentRepository.save(comment);
    }
    //댓글조회
    public List<CommentResponseDto> getComments(Long postId) {
        // 댓글들 중 게시글id가 넘어온 postId와 같은 값들을 (수정시간기준 내림차순으로) 가져온다.
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        // commentId, 작성자닉네임 , 수정시간, 댓글 내용
        List<CommentResponseDto> commentList = new ArrayList<>();
        if(!comments.isEmpty()) {
            for (int i=0; i<comments.size(); i++) {
                String content = comments.get(i).getContent();
                commentList.add(new CommentResponseDto(content));
            }
        }
        return commentList;
    }


    //댓글 수정
    @Transactional
    public void updateComment(Long commentId, UserDetailsImpl userDetails,CommentRequestDto commentRequestDto) {
        // 있는 댓글인지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다")
        );
        // 로그인한 사용자 != 댓글 작성자
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("사용자 정보가 달라 삭제할 수 없습니다");
        }
        comment.update(commentRequestDto);
    }


    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        // 있는 댓글인지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다")
        );
        // 로그인한 사용자 != 댓글 작성자
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("사용자 정보가 달라 삭제할 수 없습니다");
        }
        commentRepository.deleteById(commentId);
    }

}
