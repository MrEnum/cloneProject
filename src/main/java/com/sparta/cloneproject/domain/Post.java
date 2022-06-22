package com.sparta.cloneproject.domain;

import com.sparta.cloneproject.dto.post.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Post extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column
    private String imageUrl;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

//    @JoinColumn(name="Comment_Id")
//    @OneToMany
//    private List<Comment> Comment;

//    @ManyToMany
//    private List<Folder> folderList;

    @Column
    private String fileName;


    public Post(PostRequestDto postRequestDto, User user){
        this.imageUrl = postRequestDto.getImageUrl();
        this.title = postRequestDto.getTitle();
        this.description = postRequestDto.getDescription();
        this.user = user;
    }
    public void update(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.imageUrl = postRequestDto.getImageUrl();
        this.fileName = postRequestDto.getFileName();
        this.description = postRequestDto.getDescription();
    }
}
