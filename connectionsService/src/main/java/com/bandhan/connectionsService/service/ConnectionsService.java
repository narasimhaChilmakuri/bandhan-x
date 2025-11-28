package com.bandhan.connectionsService.service;

import com.bandhan.connectionsService.auth.AuthContextHolder;
import com.bandhan.connectionsService.entity.Person;
import com.bandhan.connectionsService.events.ConnectionAccepted;
import com.bandhan.connectionsService.events.ConnectionRequested;
import com.bandhan.connectionsService.exeception.BadRequestException;
import com.bandhan.connectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, ConnectionAccepted> connectionAcceptedKafkaTemplate;
    private final KafkaTemplate<Long, ConnectionRequested> connectionRequestedKafkaTemplate;


    public List<Person> getFirstDegreeConnectionsOfUser(Long userId) {
        return personRepository.getFirstDegreeConnections(userId);
    }

    public List<Person> getSecondDegreeConnectionsOfUser(Long userId) {
        return personRepository.getSecondDegreeConnections(userId);
    }

    public List<Person> getThirdDegreeConnectionsOfUser(Long userId) {
        return personRepository.getThirdDegreeConnections(userId);
    }

    public void sendConnectionRequest(Long toUserId) {
        Long fromUserId = AuthContextHolder.getCurrentUserId();

        if(fromUserId.equals(toUserId)){
            log.warn("UserId: {} cannot send connection request to themselves", fromUserId);
            throw new BadRequestException("Cannot send connection request to oneself");
        }

        boolean alreadyRequested = personRepository.connectionRequestExists(fromUserId, toUserId);
        if(alreadyRequested){
            log.warn("Connection request from userId: {} to userId: {} already exists", fromUserId, toUserId);
            throw new BadRequestException("Connection request already sent");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(fromUserId, toUserId);
        if(alreadyConnected){
            log.warn("UserId: {} and userId: {} are already connected", fromUserId, toUserId);
            throw new BadRequestException("Users are already connected");
        }

        ConnectionRequested connectionRequested = ConnectionRequested.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .build();
        connectionRequestedKafkaTemplate.send("connection_request_topic", connectionRequested);

        log.info("Connection request sent successfully from userId: {} to userId: {}", fromUserId, toUserId);
        personRepository.sendConnectionRequest(fromUserId, toUserId);

    }

    public void acceptConnectionRequest(Long toUserId) {
        Long fromUserId = AuthContextHolder.getCurrentUserId();

        log.info("Trying to accept connection request from userId: {} to userId: {}", fromUserId, toUserId);

        if(fromUserId.equals(toUserId)){
            log.warn("UserId: {} cannot accept connection request to themselves", fromUserId);
            throw new BadRequestException("Cannot send connection request to oneself");
        }

        boolean alreadyRequested = personRepository.connectionRequestExists(fromUserId, toUserId);
        if(!alreadyRequested){
            log.warn("No request to accept from userId: {} to userId: {}", fromUserId, toUserId);
            throw new BadRequestException("No request to accept");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(fromUserId, toUserId);
        if(alreadyConnected){
            log.warn("cannot accept request while UserId: {} and userId: {} are already connected", fromUserId, toUserId);
            throw new BadRequestException("Users are already connected");
        }

        ConnectionAccepted connectionAccepted = ConnectionAccepted.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .build();
        connectionAcceptedKafkaTemplate.send("connection_accepted_topic", connectionAccepted);


        log.info("Connection request accepted successfully from userId: {} to userId: {}", fromUserId, toUserId);
        personRepository.acceptConnectionRequest(fromUserId, toUserId);
    }

    public void rejectConnectionRequest(Long toUserId) {
        Long fromUserId = AuthContextHolder.getCurrentUserId();

        if(fromUserId.equals(toUserId)){
            log.warn("UserId: {} cannot reject connection request to themselves", fromUserId);
            throw new BadRequestException("Cannot send connection request to oneself");
        }

        boolean alreadyRequested = personRepository.connectionRequestExists(fromUserId, toUserId);
        if(!alreadyRequested){
            log.warn("No request to reject from userId: {} to userId: {}", fromUserId, toUserId);
            throw new BadRequestException("No request to reject");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(fromUserId, toUserId);
        if(alreadyConnected){
            log.warn("cannot reject request while UserId: {} and userId: {} are already connected", fromUserId, toUserId);
            throw new BadRequestException("Users are already connected");
        }


        log.info("Connection request rejected successfully from userId: {} to userId: {}", fromUserId, toUserId);
        personRepository.rejectConnectionRequest(fromUserId, toUserId);
    }
}
