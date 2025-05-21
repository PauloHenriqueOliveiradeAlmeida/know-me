package com.know_me.know_me.features.relationship.infra.entities;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.Set;

@Node("Interest")
public class InterestEntity {
    @Id
    public final String name;

    @Relationship(type = "HAS_INTEREST", direction = Relationship.Direction.OUTGOING)
    public Set<PersonEntity> persons;

    public InterestEntity(String name) {
        this.name = name;
     }
}