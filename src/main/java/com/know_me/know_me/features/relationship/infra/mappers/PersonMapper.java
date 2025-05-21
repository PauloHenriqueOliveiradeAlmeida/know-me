package com.know_me.know_me.features.relationship.infra.mappers;

import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.features.relationship.infra.entities.InterestEntity;
import com.know_me.know_me.features.relationship.infra.entities.IsFriendOfRelation;
import com.know_me.know_me.features.relationship.infra.entities.PersonEntity;
import com.know_me.know_me.shared.domain.valueobjects.ID;

import java.util.Set;
import java.util.stream.Collectors;

public class PersonMapper {

    public static PersonEntity toEntity(Person person) {
        PersonEntity personEntity = new PersonEntity(
                person.id.value(),
                person.name,
                PersonMapper.interestsToEntity(person.getInterests())
        );
        personEntity.friends = PersonMapper.friendsToEntity(person.getFriends());
        return personEntity;
    }
    public static Person toDomain(PersonEntity personEntity) {
        Set<Person> friends = personEntity.friends.stream().map(friend -> PersonMapper.toDomain(friend.person)).collect(Collectors.toSet());
        Set<String> interests = personEntity.interests.stream().map(interestEntity -> interestEntity.name).collect(Collectors.toSet());
        return new Person(
                new ID(personEntity.id),
                personEntity.name,
                interests,
                friends
        );
    }

    private static Set<InterestEntity> interestsToEntity(Set<Interest> interests) {
        return interests.stream().map(interest -> new InterestEntity(interest.value())).collect(Collectors.toSet());
    }

    private static Set<IsFriendOfRelation> friendsToEntity(Set<Person> friends) {
        return friends.stream().map(
            friend -> new IsFriendOfRelation(
                new PersonEntity(
                        friend.id.value(),
                        friend.name,
                        PersonMapper.interestsToEntity(friend.getInterests())
                )
            )
        ).collect(Collectors.toSet());
    }
}
