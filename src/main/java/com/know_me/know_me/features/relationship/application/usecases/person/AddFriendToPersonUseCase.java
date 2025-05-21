package com.know_me.know_me.features.relationship.application.usecases.person;

import com.know_me.know_me.features.relationship.application.exceptions.InvalidInputException;
import com.know_me.know_me.features.relationship.application.exceptions.NotFoundException;
import com.know_me.know_me.features.relationship.application.usecases.person.output.PersonOutput;
import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.infra.repositories.person.PersonRepository;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AddFriendToPersonUseCase {
	private final PersonRepository personRepository;

	public AddFriendToPersonUseCase(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public PersonOutput execute(ID personId, ID friendId) {
		Person person = personRepository.findById(personId);
		if (person == null) {
			throw new NotFoundException("Pessoa não encontrada");
		}

		Person friend = personRepository.findById(friendId);
		if (friend == null) {
			throw new NotFoundException("Amigo não encontrado");
		}

		person.addFriend(friend);
		personRepository.save(person);

		return new PersonOutput(
			person.id,
			person.name,
			person.getInterests(),
			person.getFriends().stream().map(friendPerson -> friendPerson.id).collect(Collectors.toSet())
		);
	}
}
