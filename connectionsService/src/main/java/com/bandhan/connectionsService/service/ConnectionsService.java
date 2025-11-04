package com.bandhan.connectionsService.service;

import com.bandhan.connectionsService.entity.Person;
import com.bandhan.connectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;


    public List<Person> getFirstDegreeConnectionsOfUser(Long userId) {
        return personRepository.getFirstDegreeConnections(userId);
    }

    public List<Person> getSecondDegreeConnectionsOfUser(Long userId) {
        return personRepository.getSecondDegreeConnections(userId);
    }

    public List<Person> getThirdDegreeConnectionsOfUser(Long userId) {
        return personRepository.getThirdDegreeConnections(userId);
    }
}
