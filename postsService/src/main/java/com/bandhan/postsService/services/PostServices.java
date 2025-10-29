package com.bandhan.postsService.services;


import com.bandhan.postsService.dto.PostCreateRequestDto;
import com.bandhan.postsService.dto.PostDto;
import com.bandhan.postsService.entity.Post;
import com.bandhan.postsService.exception.ResourceNotFoundException;
import com.bandhan.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServices {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;



    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long UserId) {
        log.info("Creating a new post for user with ID: {}", UserId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setCreatedAt(LocalDateTime.now());
        post.setUserId(UserId);
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getPostById(Long id) {
        log.info("Fetching post with ID: {}", id);
        Post post = postRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Post not found with ID: " + id));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {

        log.info("Fetching all posts for user with ID: {}", userId);
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }
}
