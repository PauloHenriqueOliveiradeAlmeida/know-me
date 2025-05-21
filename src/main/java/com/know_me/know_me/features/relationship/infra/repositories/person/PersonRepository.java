package com.know_me.know_me.features.relationship.infra.repositories.person;

import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.features.relationship.infra.entities.InterestEntity;
import com.know_me.know_me.features.relationship.infra.entities.PersonEntity;
import com.know_me.know_me.features.relationship.infra.mappers.PersonMapper;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {
    private final IPersonRepository personRepository;
    public PersonRepository(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void save(Person person) {

        PersonEntity newPerson = PersonMapper.toEntity(
            new Person(
                person.id,
                person.name,
                person.getInterests().stream().map(Interest::value).collect(Collectors.toSet()),
                person.getFriends()
            )
        );

        personRepository.save(newPerson);
    }

    public Person findById(ID id) {
        var person = personRepository.findById(id.value());
        return person.map(PersonMapper::toDomain).orElse(null);
    }

    public void deleteById(ID id) {
        personRepository.deleteById(id.value());
    }

    public List<Person> findpersonWithSameInterests(ID id) {
        return personRepository.findpersonWithSameInterests(id.value()).stream().map(PersonMapper::toDomain).toList();
    }
}
