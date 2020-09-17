package com.dankunlee.forumapp.controller;

import com.dankunlee.forumapp.entity.Comment;
import com.dankunlee.forumapp.entity.Post;
import com.dankunlee.forumapp.repository.CommentRepository;
import com.dankunlee.forumapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @PutMapping("/api/post/{id}/comment")
    public Comment createComment(@PathVariable Long id, @Valid @RequestBody Comment comment) {
        Post post = postRepository.findById(id).get();
        comment.setPost(post);
        commentRepository.save(comment);
        return comment;
    }

    @GetMapping("/api/post/{id}/comment")
    public List<Comment> getComments(@PathVariable Long id) {
        Post post = postRepository.findById(id).get();
        List<Comment> comments = commentRepository.findByPost(post);
        return comments;
    }

    @PostMapping("/api/post/{postId}/comment/{commentId}")
    public Comment updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                 @Valid @RequestBody Comment updatedComment) {
//        Post post = postRepository.findById(postId).get();
        Comment comment = commentRepository.findById(commentId).get();
        comment.setContent(updatedComment.getContent());
        comment.setWriter(updatedComment.getWriter());
        commentRepository.save(comment);

//        return comment;
        return commentRepository.findById(commentId).get();
    }

    @DeleteMapping("/api/post/{postId}/comment/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentRepository.deleteById(commentId);
        return "Deleted";
    }
}
