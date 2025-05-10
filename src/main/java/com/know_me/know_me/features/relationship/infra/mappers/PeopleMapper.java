package com.know_me.know_me.features.relationship.infra.mappers;

import com.know_me.know_me.features.relationship.domain.entities.People;
import com.know_me.know_me.features.relationship.infra.entities.PeopleEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class PeopleMapper {
    public static PeopleEntity toEntity(People people) {
        Set<PeopleEntity> friends = people.getFriends().stream().map(PeopleMapper::toEntity).collect(Collectors.toSet());
        return new PeopleEntity(people.id, people.name, people.getInterests(), friends);
    }

    public static People toDomain(PeopleEntity peopleEntity) {
        Set<People> friends = peopleEntity.friends.stream().map(PeopleMapper::toDomain).collect(Collectors.toSet());
        return new People(peopleEntity.id, peopleEntity.name, peopleEntity.interests, friends);
    }
}
