package com.know_me.know_me.features.relationship.services;

import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.features.relationship.repositories.PersonRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person createPerson(String name, Set<String> interestNames) {
        Set<Interest> interests = new HashSet<>();
        interestNames.forEach(i -> interests.add(new Interest(i)));
        Person person = new Person(ID.random(), name, interests, new HashSet<>());
        repository.save(person);
        return person;
    }

    public void addFriend(ID personId, ID friendId) {
        repository.addFriend(personId, friendId);
    }

}
