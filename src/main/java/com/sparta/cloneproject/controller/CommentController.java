package com.sparta.cloneproject.controller;

import com.sparta.cloneproject.dto.post.CommentRequestDto;
import com.sparta.cloneproject.dto.post.CommentResponseDto;
import com.sparta.cloneproject.security.UserDetailsImpl;
import com.sparta.cloneproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    //댓글 작성
    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(postId, commentRequestDto, userDetails);
        return "댓글 작성이 완료되었습니다.";
    }

    //댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    //댓글 수정
    @PutMapping("/posts/post/{commentId}")
    public String updateComment(@PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(commentId, userDetails, commentRequestDto);
        return "댓글 수정이 완료되었습니다.";
    }

    //댓글 삭제
    @DeleteMapping("/posts/comments/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails);
        return "댓글 삭제가 완료되었습니다.";
    }
}
