package com.know_me.know_me.features.relationship.entrypoints.http.controllers;

import com.know_me.know_me.features.relationship.application.usecases.people.FindPersonUseCase;
import com.know_me.know_me.features.relationship.application.usecases.people.output.PersonOutput;
import com.know_me.know_me.features.relationship.application.usecases.people.FindPersonWithSameInterestsUseCase;
import com.know_me.know_me.features.relationship.application.usecases.people.FindRelatedFriendsByPersonUseCase;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.features.relationship.entrypoints.http.dtos.response.PersonResponseDto;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import com.know_me.know_me.shared.entrypoints.http.dtos.response.SuccessResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PersonController {
	private final FindPersonUseCase findPersonUseCase;
	private final FindPersonWithSameInterestsUseCase findPeopleWithSameInterestsUseCase;
    private final FindRelatedFriendsByPersonUseCase findRelatedFriendsByPersonUseCase;

	public PersonController(
		FindPersonWithSameInterestsUseCase findPeopleWithSameInterestsUseCase,
		FindRelatedFriendsByPersonUseCase findRelatedFriendsByPersonUseCase,
		FindPersonUseCase findPersonUseCase
	) {
		this.findPeopleWithSameInterestsUseCase = findPeopleWithSameInterestsUseCase;
        this.findRelatedFriendsByPersonUseCase = findRelatedFriendsByPersonUseCase;
        this.findPersonUseCase = findPersonUseCase;
	}

	@ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{peopleId}", produces = "application/json")
    public SuccessResponseDto<PersonResponseDto> findPersonById(@PathVariable ID peopleId) {
        PersonOutput person = findPersonUseCase.execute(peopleId);
		Set<String> interests = person.interests().stream().map(Interest::name).collect(Collectors.toSet());
        return new SuccessResponseDto<>(
			"Pessoa encontrada com sucesso",
	        new PersonResponseDto(
				person.id().value().toString(),
		        person.name(),
                interests
	        )
        );
    }

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/{peopleId}/interests/match", produces = "application/json")
	public SuccessResponseDto<List<PersonResponseDto>> findPeopleWithSameInterests(@PathVariable ID peopleId) {
		List<PersonOutput> peoples = findPeopleWithSameInterestsUseCase.execute(peopleId);
		return new SuccessResponseDto<>(
            "Pessoas com interesses semelhantes encontradas com sucesso",
			peoples.stream().map(
			people -> new PersonResponseDto(
				people.id().value().toString(),
				people.name(),
				people.interests().stream().map(Interest::name).collect(Collectors.toSet())
			)).toList());
	}

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{peopleId}/related-friends", produces = "application/json")
    public SuccessResponseDto<List<PersonResponseDto>> findRelatedFriendsByPersonId(@PathVariable ID peopleId) {
        List<PersonOutput> relatedFriends = findRelatedFriendsByPersonUseCase.execute(peopleId);
        return new SuccessResponseDto<>(
			"Amigos relacionados encontrados com sucesso",
	        relatedFriends.stream().map(
                relatedFriend -> new PersonResponseDto(
                        relatedFriend.id().value().toString(),
                        relatedFriend.name(),
                        relatedFriend.interests().stream().map(Interest::name).collect(Collectors.toSet())
                )).toList());
    }
}
