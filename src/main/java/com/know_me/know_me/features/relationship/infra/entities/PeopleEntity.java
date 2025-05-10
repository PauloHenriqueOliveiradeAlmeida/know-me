package com.know_me.know_me.features.relationship.infra.entities;

import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node("Person")
public class PeopleEntity {
    @Id
    public final ID id;

    public final String name;

    @Relationship("HAS_INTEREST")
    public final Set<Interest> interests;
    public final Set<PeopleEntity> friends;

    public PeopleEntity(ID id, String name, Set<Interest> interests, Set<PeopleEntity> friends) {
        this.id = id;
        this.name = name;
        this.interests = interests;
        this.friends = friends;
    }
}


