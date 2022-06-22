package com.sparta.cloneproject.repository;

import com.sparta.cloneproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.URLConnection;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByIdAndUserId(Long postId, Long userId);

    Page<Post> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
}
