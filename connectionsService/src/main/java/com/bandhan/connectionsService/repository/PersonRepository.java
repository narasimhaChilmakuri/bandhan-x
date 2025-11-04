package com.bandhan.connectionsService.repository;

import com.bandhan.connectionsService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface PersonRepository extends Neo4jRepository<Person,Long> {

    Optional<Person> findByUserId(Long uerId);

    @Query("match (personA:Person) -[:CONNECTED_TO]- (personB:Person) " +
            "where personA.id = $userId " +
            "return personB")
    List<Person> getFirstDegreeConnections(Long userId);

    @Query("MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person)-[:CONNECTED_TO]-(personC:Person) " +
            "WHERE personA.id = $userId AND NOT (personA)-[:CONNECTED_TO]-(personC) AND personC.id <> $userId " +
            "RETURN DISTINCT personC")
    List<Person> getSecondDegreeConnections(Long userId);


    @Query("MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person)-[:CONNECTED_TO]-(personC:Person)-[:CONNECTED_TO]-(personD:Person) " +
            "WHERE personA.id = $userId AND NOT (personA)-[:CONNECTED_TO]-(personD) AND personD.id <> $userId " +
            "RETURN DISTINCT personD")
    List<Person> getThirdDegreeConnections(Long userId);

}