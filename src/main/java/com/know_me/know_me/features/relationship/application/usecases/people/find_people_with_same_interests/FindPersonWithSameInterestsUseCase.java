package com.know_me.know_me.features.relationship.application.usecases.people.find_people_with_same_interests;

import com.know_me.know_me.features.relationship.application.exceptions.NotFoundException;
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

    public List<FindPersonWithSameInterestsOutput> execute(ID peopleId) {
        Person people = peopleRepository.findById(peopleId);
        if (people == null) {
            throw new NotFoundException("Pessoa não encontrada");
        }
        List<Person> peoples = peopleRepository.findPeopleWithSameInterests(peopleId);

        return peoples.stream().map(
                p -> new FindPersonWithSameInterestsOutput(
                        p.id, p.name, p.getInterests())
        ).toList();
    }
}
