package com.know_me.know_me.features.relationship.application.usecases.people;

import com.know_me.know_me.features.relationship.application.exceptions.NotFoundException;
import com.know_me.know_me.features.relationship.application.usecases.people.output.PersonOutput;
import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.infra.repositories.people.PersonRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindPersonWithSameInterestsUseCase {
    private final PersonRepository peopleRepository;

    public FindPersonWithSameInterestsUseCase(PersonRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<PersonOutput> execute(ID peopleId) {
        Person people = peopleRepository.findById(peopleId);
        if (people == null) {
            throw new NotFoundException("Pessoa não encontrada");
        }

        List<Person> peoples = peopleRepository.findPeopleWithSameInterests(peopleId);
        return peoples.stream().map(
                p -> new PersonOutput(
                        p.id, p.name, p.getInterests())
        ).toList();
    }
}
