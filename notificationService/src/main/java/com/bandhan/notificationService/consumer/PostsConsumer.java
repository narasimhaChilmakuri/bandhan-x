package com.bandhan.notificationService.consumer;

import com.bandhan.notificationService.entity.Notification;
import com.bandhan.notificationService.service.NotificationService;
import com.bandhan.postsService.events.PostCreated;
import com.bandhan.postsService.events.PostLiked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreated(PostCreated postCreated){

        System.out.println("Received Post Created Event: " + postCreated);

        String message = "New post created by user " + postCreated.getOwnerUserId() +
                ": " + postCreated.getContent();

        Notification notification = Notification.builder()
                .userId(postCreated.getUserId())
                .message(message)
                .build();

        notificationService.addNotification(notification);
    }

    @KafkaListener(topics = "post_liked_topic")
    public void handlePostLiked(PostLiked postLiked){

        System.out.println("Received Post Created Event: " + postLiked);

       String message = "Your post with ID " + postLiked.getPostId() +
                " was liked by user " + postLiked.getLikedByUserId();

       Notification notification = Notification.builder()
                .userId(postLiked.getOwnerUserId())
                .message(message)
                .build();

        notificationService.addNotification(notification);
    }


}
