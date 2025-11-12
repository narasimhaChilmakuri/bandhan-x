package com.bandhan.notificationService.consumer;

import com.bandhan.connectionsService.events.ConnectionAccepted;
import com.bandhan.connectionsService.events.ConnectionRequested;
import com.bandhan.notificationService.entity.Notification;
import com.bandhan.notificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsConsumer {

    private final NotificationService notificationService;


    @KafkaListener(topics = "connection_request_topic")
    public void handleConnectionRequested(ConnectionRequested connectionRequested){

        log.info("Received ConnectionRequested event: {}", connectionRequested);

        String message = "You have a new connection request from userId: " + connectionRequested.getFromUserId();

        Notification notification = Notification.builder()
                .userId(connectionRequested.getToUserId())
                .message(message)
                .build();

        notificationService.addNotification(notification);
    }

    @KafkaListener(topics = "connection_accepted_topic")
    public void handleConnectionAccepted(ConnectionAccepted connectionAccepted){

        log.info("Received connectionAccepted event: {}", connectionAccepted);

        String message = "Your Connection Requested has been accepted by: " + connectionAccepted.getFromUserId();

        Notification notification = Notification.builder()
                .userId(connectionAccepted.getToUserId())
                .message(message)
                .build();

        notificationService.addNotification(notification);
    }


}
