package com.dankunlee.forumapp.repository;

import com.dankunlee.forumapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findAll(Pageable pageable); // repository for paging

    @Query (
            value = "SELECT post FROM Post post WHERE post.title LIKE %:title% OR post.content LIKE %:content%",
            countQuery = "SELECT COUNT(post.id) FROM Post post WHERE post.title LIKE %:title% OR post.content LIKE %:content%"
    )
    public Page<Post> findAllSearch(String title, String content, Pageable pageable); // repository for searching
}
