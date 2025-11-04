package com.bandhan.connectionsService.controller;


import com.bandhan.connectionsService.entity.Person;
import com.bandhan.connectionsService.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId)
    {
        log.info("Received request to get first degree connections for userId: {}", userId);
        List<Person> personList = connectionsService.getFirstDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/{userId}/second-degree")
    public ResponseEntity<List<Person>> getSecondDegreeConnections(@PathVariable Long userId)
    {
        log.info("Received request to get second degree connections for userId: {}", userId);
        List<Person> personList = connectionsService.getSecondDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/{userId}/third-degree")
    public ResponseEntity<List<Person>> getThirdDegreeConnections(@PathVariable Long userId)
    {
        log.info("Received request to get third degree connections for userId: {}", userId);
        List<Person> personList = connectionsService.getThirdDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(personList);
    }



}
