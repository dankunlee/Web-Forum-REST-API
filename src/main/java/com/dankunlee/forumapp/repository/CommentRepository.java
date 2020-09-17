package com.dankunlee.forumapp.repository;

import com.dankunlee.forumapp.entity.Comment;
import com.dankunlee.forumapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPost(Post post); //JPA's derived query method
}
