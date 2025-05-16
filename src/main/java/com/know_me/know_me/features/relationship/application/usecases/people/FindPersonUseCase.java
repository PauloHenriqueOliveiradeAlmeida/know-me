package com.know_me.know_me.features.relationship.application.usecases.people;

import com.know_me.know_me.features.relationship.application.exceptions.NotFoundException;
import com.know_me.know_me.features.relationship.application.usecases.people.output.PersonOutput;
import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.infra.repositories.people.PersonRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

@Service
public class FindPersonUseCase {
	private final PersonRepository personRepository;

	public FindPersonUseCase(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public PersonOutput execute(ID peopleId) {
		Person person = personRepository.findById(peopleId);
		if (person == null) {
			throw new NotFoundException("Pessoa não encontrada");
		}
		return new PersonOutput(person.id, person.name, person.getInterests());
	}
}
