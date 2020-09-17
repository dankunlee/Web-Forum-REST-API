package com.dankunlee.forumapp.repository;

import com.dankunlee.forumapp.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT file FROM File file WHERE post_id = :id") // post_id already exists in UploadedFile entity
    public List<File> findAllByPostId(Long id);
}
