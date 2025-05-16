package com.know_me.know_me.features.relationship.application.usecases.people;

import com.know_me.know_me.features.relationship.application.exceptions.NotFoundException;
import com.know_me.know_me.features.relationship.application.usecases.people.output.PersonOutput;
import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.infra.repositories.people.PersonRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindRelatedFriendsByPersonUseCase {
    private final PersonRepository personRepository;
    FindRelatedFriendsByPersonUseCase(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonOutput> execute(ID peopleId) {
        Person person = personRepository.findById(peopleId);
        if (person == null) {
            throw new NotFoundException("Pessoa não encontrada");
        }

        List<Person> relatedFriends = personRepository.findRelatedFriendsByPersonId(peopleId);
        return relatedFriends.stream().map(
                p -> new PersonOutput(
                        p.id, p.name, p.getInterests())
        ).toList();
    }
}
