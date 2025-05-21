package com.know_me.know_me.features.relationship.infra.repositories.person;

import com.know_me.know_me.features.relationship.infra.entities.InterestEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

interface IInterestRepository extends Neo4jRepository<InterestEntity, String> {
	@Query("""
			MATCH (interest:Interest)
			WHERE interest.name = $name
			RETURN interest
	""")
	InterestEntity findByName(String name);
}
