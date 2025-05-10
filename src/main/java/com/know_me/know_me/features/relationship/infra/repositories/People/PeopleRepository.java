package com.know_me.know_me.features.relationship.infra.repositories.People;

import com.know_me.know_me.features.relationship.domain.entities.People;
import com.know_me.know_me.features.relationship.infra.mappers.PeopleMapper;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeopleRepository {
    private final IPeopleRepository peopleRepository;

    public PeopleRepository(IPeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public void save(People people) {
        peopleRepository.save(PeopleMapper.toEntity(people));
    }

    public People findById(ID id) {
        var people = peopleRepository.findById(id);
        return people.map(PeopleMapper::toDomain).orElse(null);
    }

    public void delete(People people) {
        peopleRepository.delete(PeopleMapper.toEntity(people));
    }

    public List<People> findPeopleWithSameInterests(ID id) {
        return peopleRepository.findPeopleWithSameInterests(id).stream().map(PeopleMapper::toDomain).toList();
    }
}
