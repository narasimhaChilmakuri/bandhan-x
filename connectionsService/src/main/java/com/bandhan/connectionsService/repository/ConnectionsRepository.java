package com.bandhan.connectionsService.repository;

import com.bandhan.connectionsService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionsRepository extends Neo4jRepository<Person,Long> {
}
