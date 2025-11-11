package com.bandhan.postsService.services;


import com.bandhan.postsService.auth.AuthContextHolder;
import com.bandhan.postsService.client.ConnectionsServiceClient;
import com.bandhan.postsService.dto.PersonDto;
import com.bandhan.postsService.dto.PostCreateRequestDto;
import com.bandhan.postsService.dto.PostDto;
import com.bandhan.postsService.entity.Post;
import com.bandhan.postsService.events.PostCreated;
import com.bandhan.postsService.exception.ResourceNotFoundException;
import com.bandhan.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServices {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private ConnectionsServiceClient connectionsServiceClient;

    private KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate;


    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long UserId) {
        log.info("Creating a new post for user with ID: {}", UserId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setCreatedAt(LocalDateTime.now());
        post.setUserId(UserId);
        postRepository.save(post);

        //get all connections of current user
        List<PersonDto> personDto = connectionsServiceClient.getFirstDegreeConnections(AuthContextHolder.getCurrentUserId());



        for(PersonDto person : personDto){
            PostCreated postCreated = new PostCreated();
            postCreated.setPostId(post.getId());
            postCreated.setOwnerUserId(post.getUserId());
            postCreated.setContent(post.getContent());
            postCreated.setUserId(person.getId());
            postCreatedKafkaTemplate.send("post-created-topic",postCreated);
        }


        //send notification to all connections

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
