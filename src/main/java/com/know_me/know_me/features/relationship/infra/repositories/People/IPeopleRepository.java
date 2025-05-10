package com.know_me.know_me.features.relationship.infra.repositories.People;

import com.know_me.know_me.features.relationship.infra.entities.PeopleEntity;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface IPeopleRepository extends Neo4jRepository<PeopleEntity, ID> {
    @Query("""
            MATCH (people1:People {id: $id})-[:HAS_INTEREST]->(interest:Interest)<-[:HAS_INTEREST]-(people2:People)
            WHERE people1.id <> people2.id
            RETURN DISTINCT people2
            """)
    List<PeopleEntity> findPeopleWithSameInterests(ID id);
}
