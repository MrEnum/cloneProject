package com.sparta.cloneproject.repository;

import com.sparta.cloneproject.domain.Folder;
import com.sparta.cloneproject.repository.Mapping.PostMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Boolean existsByUserIdAndFolder(Long userId, String folder);

    List<PostMapping> findAllByUserIdAndFolder(Long userId, String folderName);
}
