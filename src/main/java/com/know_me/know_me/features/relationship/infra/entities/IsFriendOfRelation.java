package com.know_me.know_me.features.relationship.infra.entities;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;
import java.util.UUID;

@RelationshipProperties
public class IsFriendOfRelation {
	@RelationshipId
	public Long id;

	@TargetNode
	public PersonEntity person;

	public IsFriendOfRelation(PersonEntity person) {
		this.person = person;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof IsFriendOfRelation that)) return false;
		return Objects.equals(person.id, that.person.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(person.id);
	}
}
