package com.know_me.know_me.features.relationship.infra.repositories.people;

import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.infra.mappers.PeopleMapper;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {
    private final IPersonRepository peopleRepository;

    public PersonRepository(IPersonRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public void save(Person people) {
        peopleRepository.save(PeopleMapper.toEntity(people));
    }

    public Person findById(ID id) {
        var people = peopleRepository.findById(id.value());
        return people.map(PeopleMapper::toDomain).orElse(null);
    }

    public void delete(Person people) {
        peopleRepository.delete(PeopleMapper.toEntity(people));
    }

    public List<Person> findPeopleWithSameInterests(ID id) {
        return peopleRepository.findPeopleWithSameInterests(id.value()).stream().map(PeopleMapper::toDomain).toList();
    }

    public  List<Person> findRelatedFriendsByPersonId(ID id) {
        return peopleRepository.findRelatedFriendsByPersonId(id.value()).stream().map(PeopleMapper::toDomain).toList();
    }
}
