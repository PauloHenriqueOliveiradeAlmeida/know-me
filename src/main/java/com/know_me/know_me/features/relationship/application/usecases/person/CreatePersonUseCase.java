package com.know_me.know_me.features.relationship.application.usecases.person;

import com.know_me.know_me.features.relationship.application.usecases.person.input.CreatePersonInput;
import com.know_me.know_me.features.relationship.application.usecases.person.output.PersonOutput;
import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.infra.repositories.person.PersonRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreatePersonUseCase
{
	private final PersonRepository personRepository;
	CreatePersonUseCase(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public PersonOutput execute(CreatePersonInput input) {
		Person person = new Person(
			ID.random(),
			input.name(),
			input.interests(),
			new HashSet<>()
		);

		personRepository.save(person);

		Set<ID> friendIds = person.getFriends().stream()
			.map(friend -> friend.id)
			.collect(Collectors.toSet());
		return new PersonOutput(
			person.id,
			person.name,
			person.getInterests(),
			friendIds
		);
	}
}
