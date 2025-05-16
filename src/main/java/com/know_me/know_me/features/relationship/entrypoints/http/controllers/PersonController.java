package com.know_me.know_me.features.relationship.entrypoints.http.controllers;

import com.know_me.know_me.features.relationship.application.usecases.people.find_people_with_same_interests.FindPersonWithSameInterestsOutput;
import com.know_me.know_me.features.relationship.application.usecases.people.find_people_with_same_interests.FindPersonWithSameInterestsUseCase;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.features.relationship.entrypoints.http.dtos.response.FindPersonWithSameInterestsResponseDto;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import com.know_me.know_me.shared.entrypoints.http.dtos.response.SuccessResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PersonController {
    private final FindPersonWithSameInterestsUseCase findPeopleWithSameInterestsUseCase;

    public PersonController(FindPersonWithSameInterestsUseCase findPeopleWithSameInterestsUseCase) {
        this.findPeopleWithSameInterestsUseCase = findPeopleWithSameInterestsUseCase;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{peopleId}/interests/match", produces = "application/json")
    public SuccessResponseDto<List<FindPersonWithSameInterestsResponseDto>> findPeopleWithSameInterests(@PathVariable ID peopleId) {
        List<FindPersonWithSameInterestsOutput> peoples = findPeopleWithSameInterestsUseCase.execute(peopleId);
        return new SuccessResponseDto<>(peoples.stream().map(
                        people -> new FindPersonWithSameInterestsResponseDto(
                                people.id().value().toString(),
                                people.name(),
                                people.interests().stream().map(Interest::name).collect(Collectors.toSet())
                        )).toList());
    }
}
