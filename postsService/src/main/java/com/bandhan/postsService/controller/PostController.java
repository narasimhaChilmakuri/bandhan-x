package com.bandhan.postsService.controller;


import com.bandhan.postsService.auth.AuthContextHolder;
import com.bandhan.postsService.dto.PostCreateRequestDto;
import com.bandhan.postsService.dto.PostDto;
import com.bandhan.postsService.services.PostServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {

    private final PostServices postServices;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        Long userId = 1L; // TODO: get user id from auth context
        PostDto postDto = postServices.createPost(postCreateRequestDto,userId);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {

        PostDto postDto = postServices.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDto> posts = postServices.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }


}
