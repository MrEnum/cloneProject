package com.sparta.cloneproject.repository;

import com.sparta.cloneproject.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
