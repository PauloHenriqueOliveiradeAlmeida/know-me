package com.know_me.know_me.features.relationship.infra.mappers;

import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.features.relationship.infra.entities.InterestEntity;
import com.know_me.know_me.features.relationship.infra.entities.PersonEntity;
import com.know_me.know_me.shared.domain.valueobjects.ID;

import java.util.Set;
import java.util.stream.Collectors;

public class PeopleMapper {
    public static PersonEntity toEntity(Person people) {
        Set<PersonEntity> friends = people.getFriends().stream().map(PeopleMapper::toEntity).collect(Collectors.toSet());
        return new PersonEntity(
                people.id.value(),
                people.name,
                people.getInterests().stream().map(interest -> new InterestEntity(interest.name(), null)).collect(Collectors.toSet()),
                friends
        );
    }

    public static Person toDomain(PersonEntity peopleEntity) {
        Set<Person> friends = peopleEntity.friends.stream().map(PeopleMapper::toDomain).collect(Collectors.toSet());
        Set<Interest> interests = peopleEntity.interests.stream().map(interestEntity -> new Interest(interestEntity.name)).collect(Collectors.toSet());
        return new Person(
                new ID(peopleEntity.id),
                peopleEntity.name,
                interests,
                friends
        );
    }
}
