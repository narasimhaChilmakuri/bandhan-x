package com.bandhan.postsService.services;

import com.bandhan.postsService.entity.Post;
import com.bandhan.postsService.entity.PostLike;
import com.bandhan.postsService.exception.BadRequestException;
import com.bandhan.postsService.exception.ResourceNotFoundException;
import com.bandhan.postsService.repository.PostLikesRepository;
import com.bandhan.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;


    @Transactional
    public void likePost(Long postId) {
        Long userId = 1L; // Replace with actual user ID retrieval logic
        log.info("User with ID: {} Liking post with Post ID: {}", userId,postId);
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post not found with ID: " + postId));

        boolean hasAlreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyLiked){
            throw new BadRequestException("You cannot like the post again");
        }

        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLike.setCreatedAt(LocalDateTime.now());
        postLikesRepository.save(postLike);
    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L; // Replace with actual user ID retrieval logic
        log.info("User with ID: {} Unliking post with Post ID: {}", userId,postId);
        postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post not found with ID: " + postId));

        boolean hasAlreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId,postId);
        if(!hasAlreadyLiked){
            throw new BadRequestException("You have not liked this post yet");
        }

        postLikesRepository.deleteByUserIdAndPostId(userId,postId);
    }
}
