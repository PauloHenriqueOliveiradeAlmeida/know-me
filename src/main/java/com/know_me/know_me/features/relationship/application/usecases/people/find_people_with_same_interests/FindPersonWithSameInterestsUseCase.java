package com.know_me.know_me.features.relationship.application.usecases.people.find_people_with_same_interests;

import com.know_me.know_me.features.relationship.application.exceptions.NotFoundException;
import com.know_me.know_me.features.relationship.domain.entities.People;
import com.know_me.know_me.features.relationship.infra.repositories.people.PeopleRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindPeopleWithSameInterestsUseCase {
    private final PeopleRepository peopleRepository;

    public FindPeopleWithSameInterestsUseCase(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<FindPeopleWithSameInterestsOutput> execute(ID peopleId) {
        People people = peopleRepository.findById(peopleId);
        if (people == null) {
            throw new NotFoundException("Pessoa não encontrada");
        }
        List<People> peoples = peopleRepository.findPeopleWithSameInterests(peopleId);

        return peoples.stream().map(
                p -> new FindPeopleWithSameInterestsOutput(
                        p.id, p.name, p.getInterests())
        ).toList();
    }
}
