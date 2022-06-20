package com.sparta.cloneproject.repository;

import com.sparta.cloneproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String signupRequestDto);

    Optional<User> findByKakaoId(Long id);


}
