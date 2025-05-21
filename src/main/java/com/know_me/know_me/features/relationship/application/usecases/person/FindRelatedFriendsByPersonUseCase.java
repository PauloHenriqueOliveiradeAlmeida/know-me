package com.know_me.know_me.features.relationship.application.usecases.person;

import com.know_me.know_me.features.relationship.application.exceptions.NotFoundException;
import com.know_me.know_me.features.relationship.application.usecases.person.output.PersonOutput;
import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.infra.repositories.person.PersonRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FindRelatedFriendsByPersonUseCase {
    private final PersonRepository personRepository;
    FindRelatedFriendsByPersonUseCase(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonOutput> execute(ID personId) {
        Person person = personRepository.findById(personId);
        if (person == null) {
            throw new NotFoundException("Pessoa não encontrada");
        }

        Set<Person> directFriends = person.getFriends().stream().map(
                friend -> personRepository.findById(friend.id)
        ).collect(Collectors.toSet());

        Set<Person> relatedFriends = directFriends.stream().flatMap(
                friend -> friend.getFriends().stream()
        ).collect(Collectors.toSet());

        Set<Person> relatedFriendsWithFriends = relatedFriends.stream().map(
            friend -> personRepository.findById(friend.id)
        ).collect(Collectors.toSet());

        return relatedFriendsWithFriends.stream().map(
                relatedFriend -> new PersonOutput(
                        relatedFriend.id,
                        relatedFriend.name,
                        relatedFriend.getInterests(),
                        relatedFriend.getFriends().stream().map(friendPerson -> friendPerson.id).collect(Collectors.toSet())
                )
        ).toList();
    }
}
