package com.dankunlee.forumapp.controller;

import com.dankunlee.forumapp.dto.PagingDTO;
import com.dankunlee.forumapp.entity.Post;
import com.dankunlee.forumapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PagingController {
    @Autowired
    PostRepository postRepository;

    @GetMapping("/api/post/page") // ex. "/api/post/page?page=1&size=6"
    @ResponseBody
    public Page<PagingDTO> paging(@PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC)
                                   Pageable pageRequest) {
        Page<Post> page = postRepository.findAll(pageRequest);
        Page<PagingDTO> page_dto = page.map((post) -> new PagingDTO(post.getId(), post.getTitle(),
                                                                    post.getCreatedBy(), post.getCreatedDate()));
        return page_dto;
    }

    @GetMapping("/api/post/page/search") // ex. "/api/post/page/search?keyword=post&page=0&size=3"
    @ResponseBody
    public Page<PagingDTO> search(@RequestParam String keyword,
                                  @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC)
                                   Pageable pageRequest) {
        Page<Post> page = postRepository.findAllSearch(keyword, keyword, pageRequest);
        Page<PagingDTO> page_dto = page.map((post) -> new PagingDTO(post.getId(), post.getTitle(),
                post.getCreatedBy(), post.getCreatedDate()));
        return page_dto;
    }
}
