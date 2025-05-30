package com.know_me.know_me.features.relationship.application.usecases.person;

import com.know_me.know_me.features.relationship.application.exceptions.NotFoundException;
import com.know_me.know_me.features.relationship.application.usecases.person.input.RemoveInterestsFromPersonInput;
import com.know_me.know_me.features.relationship.application.usecases.person.output.PersonOutput;
import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.infra.repositories.person.PersonRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RemoveInterestsFromPersonUseCase {
	private final PersonRepository personRepository;
	public RemoveInterestsFromPersonUseCase(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public PersonOutput execute(ID personId, RemoveInterestsFromPersonInput input) {
		Person person = personRepository.findById(personId);
		if (person == null) {
			throw new NotFoundException("Pessoa não encontrada");
		}

		input.interests().forEach(person::removeInterest);
		personRepository.save(person);

		return new PersonOutput(
			person.id,
			person.name,
			person.getInterests(),
			person.getFriends().stream().map(friendPerson -> friendPerson.id).collect(Collectors.toSet())
		);
	}
}
