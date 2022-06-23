package com.sparta.cloneproject.domain;

import com.sparta.cloneproject.dto.post.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String content;

    public Comment(Post post, CommentRequestDto requestDto, User user) {
        this.post = post;
        this.user = user;
        this.content = requestDto.getContent();
    }
    public void update(CommentRequestDto commentRequestDto){
        this. content = commentRequestDto.getContent();

    }
}
