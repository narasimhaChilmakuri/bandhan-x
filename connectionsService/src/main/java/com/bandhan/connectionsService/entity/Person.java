package com.bandhan.connectionsService.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Builder
@Node
public class Person {

    @Id
    private Long id;
    private Long userId;
    private String name;

}
