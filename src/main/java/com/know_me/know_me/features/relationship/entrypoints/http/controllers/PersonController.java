package com.know_me.know_me.features.relationship.entrypoints.http.controllers;

import com.know_me.know_me.features.relationship.application.usecases.person.*;
import com.know_me.know_me.features.relationship.application.usecases.person.input.AddInterestsToPersonInput;
import com.know_me.know_me.features.relationship.application.usecases.person.input.CreatePersonInput;
import com.know_me.know_me.features.relationship.application.usecases.person.input.RemoveInterestsFromPersonInput;
import com.know_me.know_me.features.relationship.application.usecases.person.output.PersonOutput;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.features.relationship.entrypoints.http.dtos.request.AddInterestsToPersonRequestDto;
import com.know_me.know_me.features.relationship.entrypoints.http.dtos.request.CreatePersonRequestDto;
import com.know_me.know_me.features.relationship.entrypoints.http.dtos.request.RemoveInterestsFromPersonRequestDto;
import com.know_me.know_me.features.relationship.entrypoints.http.dtos.response.PersonResponseDto;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import com.know_me.know_me.shared.entrypoints.http.dtos.response.SuccessResponseDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {
	private final CreatePersonUseCase createPersonUseCase;
	private final AddFriendToPersonUseCase addFriendToPersonUseCase;
	private final FindPersonUseCase findPersonUseCase;
	private final FindPersonWithSameInterestsUseCase findpersonWithSameInterestsUseCase;
	private final FindRelatedFriendsByPersonUseCase findRelatedFriendsByPersonUseCase;
	private final AddInterestsToPersonUseCase addInterestsToPersonUseCase;
	private final RemoveFriendFromPersonUseCase removeFriendFromPersonUseCase;
	private final RemoveInterestsFromPersonUseCase removeInterestsFromPersonUseCase;

	public PersonController(
		CreatePersonUseCase createPersonUseCase,
		AddFriendToPersonUseCase addFriendToPersonUseCase,
		FindPersonWithSameInterestsUseCase findpersonWithSameInterestsUseCase,
		FindRelatedFriendsByPersonUseCase findRelatedFriendsByPersonUseCase,
		FindPersonUseCase findPersonUseCase,
		AddInterestsToPersonUseCase addInterestsToPersonUseCase,
		RemoveFriendFromPersonUseCase removeFriendFromPersonUseCase,
		RemoveInterestsFromPersonUseCase removeInterestsFromPersonUseCase
	) {
		this.createPersonUseCase = createPersonUseCase;
		this.addFriendToPersonUseCase = addFriendToPersonUseCase;
		this.findpersonWithSameInterestsUseCase = findpersonWithSameInterestsUseCase;
		this.findRelatedFriendsByPersonUseCase = findRelatedFriendsByPersonUseCase;
		this.findPersonUseCase = findPersonUseCase;
		this.addInterestsToPersonUseCase = addInterestsToPersonUseCase;
		this.removeFriendFromPersonUseCase = removeFriendFromPersonUseCase;
		this.removeInterestsFromPersonUseCase = removeInterestsFromPersonUseCase;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos")
		}
	)
	@PostMapping
	public SuccessResponseDto<PersonResponseDto> createPerson(@RequestBody CreatePersonRequestDto createPersonDto) {
		PersonOutput createdPerson = createPersonUseCase.execute(
			new CreatePersonInput(createPersonDto.name(), createPersonDto.interests())
		);

		Set<String> interests = createdPerson.interests().stream().map(Interest::value).collect(Collectors.toSet());
		Set<String> friendIds = createdPerson.friends().stream().map(id -> id.value().toString()).collect(Collectors.toSet());
		return new SuccessResponseDto<>(
			"Pessoa criada com sucesso",
			new PersonResponseDto(
				createdPerson.id().value().toString(),
				createdPerson.name(),
				interests,
				friendIds
			)
		);
	}

	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "Novo amigo adicionado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pessoas não encontradas"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos")
		}
	)
	@PatchMapping("/{personId}/friends/{friendId}")
	public SuccessResponseDto<PersonResponseDto> addFriend(@PathVariable ID personId, @PathVariable ID friendId) {
		PersonOutput person = addFriendToPersonUseCase.execute(personId, friendId);

		Set<String> interests = person.interests().stream().map(Interest::value).collect(Collectors.toSet());
		Set<String> friendIds = person.friends().stream().map(id -> id.value().toString()).collect(Collectors.toSet());
		return new SuccessResponseDto<>(
			"Novo amigo adicionado com sucesso",
			new PersonResponseDto(
				person.id().value().toString(),
				person.name(),
				interests,
				friendIds
			)
		);
	}

	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "Interesses adicionados com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
			@ApiResponse(responseCode = "400", description = "Interesses inválidos")
		}
	)
	@PatchMapping("/{personId}/interests")
	public SuccessResponseDto<PersonResponseDto> addInterests(@PathVariable ID personId, @RequestBody AddInterestsToPersonRequestDto addInterestsToPersonRequestDto) {
		AddInterestsToPersonInput input = new AddInterestsToPersonInput(addInterestsToPersonRequestDto.interests());
		PersonOutput person = addInterestsToPersonUseCase.execute(personId, input);

		return new SuccessResponseDto<>(
			"Interesses adicionados com sucesso",
			new PersonResponseDto(
				person.id().value().toString(),
				person.name(),
				person.interests().stream().map(Interest::value).collect(Collectors.toSet()),
				person.friends().stream().map(id -> id.value().toString()).collect(Collectors.toSet())
			)
		);
	}

	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
		}
	)
	@GetMapping("/{personId}")
	public SuccessResponseDto<PersonResponseDto> findPersonById(@PathVariable ID personId) {
		PersonOutput person = findPersonUseCase.execute(personId);

		Set<String> interests = person.interests().stream().map(Interest::value).collect(Collectors.toSet());
		Set<String> friendIds = person.friends().stream().map(id -> id.value().toString()).collect(Collectors.toSet());
		return new SuccessResponseDto<>(
			"Pessoa encontrada com sucesso",
			new PersonResponseDto(
				person.id().value().toString(),
				person.name(),
				interests,
				friendIds
			)
		);
	}

	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "Pessoas com interesses semelhantes encontradas com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
		}
	)
	@GetMapping("/{personId}/interests/match")
	public SuccessResponseDto<List<PersonResponseDto>> findpersonWithSameInterests(@PathVariable ID personId) {
		List<PersonOutput> persons = findpersonWithSameInterestsUseCase.execute(personId);

		List<PersonResponseDto> personsResponse = persons.stream().map(person -> {
			Set<String> interests = person.interests().stream().map(Interest::value).collect(Collectors.toSet());
			Set<String> friendIds = person.friends().stream().map(id -> id.value().toString()).collect(Collectors.toSet());
			return new PersonResponseDto(
				person.id().value().toString(),
				person.name(),
				interests,
				friendIds
			);
		}).toList();

		return new SuccessResponseDto<>(
			"Pessoas com interesses semelhantes encontradas com sucesso",
			personsResponse
		);
	}

	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "Amigos relacionados encontrados com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pessoas não encontradas")
		}
	)
	@GetMapping("/{personId}/related-friends")
	public SuccessResponseDto<List<PersonResponseDto>> findRelatedFriendsByPersonId(@PathVariable ID personId) {
		List<PersonOutput> relatedFriends = findRelatedFriendsByPersonUseCase.execute(personId);

		List<PersonResponseDto> relatedFriendsResponse = relatedFriends.stream().map(relatedFriend -> {
			Set<String> interests = relatedFriend.interests().stream().map(Interest::value).collect(Collectors.toSet());
			Set<String> friendIds = relatedFriend.friends().stream().map(id -> id.value().toString()).collect(Collectors.toSet());
			return new PersonResponseDto(
				relatedFriend.id().value().toString(),
				relatedFriend.name(),
				interests,
				friendIds
			);
		}).toList();

		return new SuccessResponseDto<>(
			"Amigos relacionados encontrados com sucesso",
			relatedFriendsResponse
		);
	}

	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "Amigo removido com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pessoas não encontradas")
		}
	)
	@DeleteMapping("/{personId}/friends/{friendId}")
	public SuccessResponseDto<PersonResponseDto> removeFriendFromPerson(@PathVariable ID personId, @PathVariable ID friendId) {
		PersonOutput person = removeFriendFromPersonUseCase.execute(personId, friendId);

		return new SuccessResponseDto<>(
			"Amigo removido com sucesso",
			new PersonResponseDto(
				person.id().value().toString(),
				person.name(),
				person.interests().stream().map(Interest::value).collect(Collectors.toSet()),
				person.friends().stream().map(id -> id.value().toString()).collect(Collectors.toSet())
			)
		);
	}

	@ResponseStatus(HttpStatus.OK)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "Interesses removidos com sucesso"),
			@ApiResponse(responseCode = "404", description = "Interesses não encontrados"),
			@ApiResponse(responseCode = "400", description = "Interesses inválidos")
		}
	)
	@DeleteMapping("/{personId}/interests")
	public SuccessResponseDto<PersonResponseDto> removeInterestsFromPerson(@PathVariable ID personId, @RequestBody RemoveInterestsFromPersonRequestDto removeInterestsFromPersonRequestDto) {
		RemoveInterestsFromPersonInput input = new RemoveInterestsFromPersonInput(removeInterestsFromPersonRequestDto.interests());
		PersonOutput person = removeInterestsFromPersonUseCase.execute(personId, input);

		return new SuccessResponseDto<>(
			"Interesses removidos com sucesso",
			new PersonResponseDto(
				person.id().value().toString(),
				person.name(),
				person.interests().stream().map(Interest::value).collect(Collectors.toSet()),
				person.friends().stream().map(id -> id.value().toString()).collect(Collectors.toSet())
			)
		);
	}
}
