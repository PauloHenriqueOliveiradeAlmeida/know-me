package com.know_me.know_me.features.relationship.infra.repositories.people;

import com.know_me.know_me.features.relationship.infra.entities.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.UUID;

public interface IPersonRepository extends Neo4jRepository<PersonEntity, UUID> {
    @Query("""
            MATCH (p:Person {id: $id})-[:HAS_INTEREST]->(:Interest)<-[:HAS_INTEREST]-(other:Person)
            WHERE p.id <> other.id
            WITH DISTINCT other
            MATCH (other)-[r:HAS_INTEREST]->(otherInterests:Interest)
            RETURN other, r, collect(otherInterests) as interests
        """)
    List<PersonEntity> findPeopleWithSameInterests(UUID id);

    @Query("""
            MATCH (p:Person {id: $id})-[:IS_FRIEND_OF]->(:Person)<-[:IS_FRIEND_OF]-(other:Person)
            WHERE p.id <> other.id
            WITH DISTINCT other
            OPTIONAL MATCH (other)-[r:HAS_INTEREST]->(i:Interest)
            RETURN other
        """)
    List<PersonEntity> findRelatedFriendsByPersonId(UUID id);
}
