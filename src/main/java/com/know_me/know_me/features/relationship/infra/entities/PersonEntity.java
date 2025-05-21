package com.know_me.know_me.features.relationship.infra.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Node("Person")
public class PersonEntity {
    @Id
    @GeneratedValue
    public final UUID id;

    public final String name;

    @Relationship(value = "HAS_INTEREST", direction = Relationship.Direction.OUTGOING)
    public Set<InterestEntity> interests;

    @Relationship(value = "IS_FRIEND_OF", direction = Relationship.Direction.OUTGOING)
    public Set<IsFriendOfRelation> friends;

    public PersonEntity(UUID id, String name, Set<InterestEntity> interests) {
        this.id = id;
        this.name = name;
        this.interests = interests;
    }
}