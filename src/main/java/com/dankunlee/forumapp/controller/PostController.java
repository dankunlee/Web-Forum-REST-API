package com.dankunlee.forumapp.controller;

import com.dankunlee.forumapp.entity.Post;
import com.dankunlee.forumapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String hello() {
        return "API";
    }

    @PutMapping("/api/post")
    public Post newPost(@Valid @RequestBody Post newPost) {
        // creates a new post in the db
        Post createdPost = postRepository.save(newPost);
        return createdPost;
    }

    @GetMapping("/api/post")
    public List<Post> getAllPost() {
        // retrieves all posts from the db
        return postRepository.findAll();
    }

    @GetMapping("/api/post/{id}")
    public Post getPost(@PathVariable String id) {
        // retrieves a post with specified id
        Long postID = Long.parseLong(id);
        Optional<Post> postOptional = postRepository.findById(postID);
        Post post = postOptional.get();
        return post;
    }

    @PostMapping("/api/post/{id}")
    public Post updatePost(@PathVariable String id, @Valid @RequestBody Post updatedPost) {
        // modifies(replaces) the post with the provided new post
        Long postID = Long.parseLong(id);
        Optional<Post> postOptional = postRepository.findById(postID);

        Post post = postOptional.get();
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        postRepository.save(post);

        return postRepository.findById(postID).get();
    }

    @DeleteMapping("/api/post/{id}")
    public String deletePost(@PathVariable String id) {
        // deletes the post with specified id
        Long postID = Long.parseLong(id);
        postRepository.deleteById(postID);
        return "Deleted";
    }
}
