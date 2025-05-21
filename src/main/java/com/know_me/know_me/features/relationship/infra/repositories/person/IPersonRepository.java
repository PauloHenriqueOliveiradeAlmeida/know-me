package com.know_me.know_me.features.relationship.infra.repositories.person;

import com.know_me.know_me.features.relationship.infra.entities.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface IPersonRepository extends Neo4jRepository<PersonEntity, UUID> {
	@Query("""
		    MATCH (person:Person)
		    WHERE person.id = $id
			CALL {
		        WITH person
		        OPTIONAL MATCH (person)-[interest_rel:HAS_INTEREST]->(interest:Interest)
		        RETURN
		            collect(DISTINCT interest) AS interests,
		            collect(DISTINCT interest_rel) as interest_rel
		    }
			CALL {
		        WITH person
		        OPTIONAL MATCH (person)-[friend_rel:IS_FRIEND_OF]->(friend:Person)
		        RETURN
		            collect(DISTINCT friend) AS friends,
		            collect(DISTINCT friend_rel) as friend_rel
		    }
			RETURN person, interests, friends, interest_rel, friend_rel
		""")
	Optional<PersonEntity> findById(UUID id);

	@Query("""
		    MATCH (p:Person {id: $id})-[:HAS_INTEREST]->(:Interest)<-[:HAS_INTEREST]-(other:Person)
		    WHERE p.id <> other.id
		    WITH DISTINCT other
		    CALL {
		        WITH other
		        OPTIONAL MATCH (other)-[interest_rel:HAS_INTEREST]->(interest:Interest)
		        RETURN
		            collect(DISTINCT interest) AS interests,
		            collect(DISTINCT interest_rel) as interest_rel
		    }
			CALL {
		        WITH other
		        OPTIONAL MATCH (other)-[friend_rel:IS_FRIEND_OF]->(friend:Person)
		        RETURN
		            collect(DISTINCT friend) AS friends,
		            collect(DISTINCT friend_rel) as friend_rel
		    }
			RETURN other, interests, friends, interest_rel, friend_rel
		""")
	List<PersonEntity> findpersonWithSameInterests(UUID id);
}
