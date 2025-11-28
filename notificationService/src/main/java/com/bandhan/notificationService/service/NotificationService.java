package com.bandhan.notificationService.service;

import com.bandhan.notificationService.entity.Notification;
import com.bandhan.notificationService.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void addNotification(Notification notification){
        notification = notificationRepository.save(notification);

        //send notification via email/SMS/push (not implemented)

        log.info("Notification saved successfully for userId: {}", notification.getUserId());
    }

}
