package com.sparta.cloneproject.repository;

import com.sparta.cloneproject.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);
}
