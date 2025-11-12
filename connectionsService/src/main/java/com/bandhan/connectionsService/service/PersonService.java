package com.bandhan.connectionsService.service;

import com.bandhan.connectionsService.entity.Person;
import com.bandhan.connectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void createPerson(Long userId, String name) {
        Person person = Person.builder().name(name).userId(userId).build();
        personRepository.save(person);
    }

}
