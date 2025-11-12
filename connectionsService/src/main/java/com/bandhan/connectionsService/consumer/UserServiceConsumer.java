package com.bandhan.connectionsService.consumer;

import com.bandhan.connectionsService.service.PersonService;
import com.bandhan.usersService.events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceConsumer {

    private final PersonService personService;

    @KafkaListener(topics = "user_created_topic")
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        log.info("Received UserCreatedEvent for userId: {}", userCreatedEvent.getUserId());
        personService.createPerson(userCreatedEvent.getUserId(), userCreatedEvent.getName());
    }



}
