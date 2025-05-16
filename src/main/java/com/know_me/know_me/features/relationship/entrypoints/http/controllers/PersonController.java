package com.know_me.know_me.features.relationship.entrypoints.http.controllers;

import com.know_me.know_me.features.relationship.application.usecases.people.find_people_with_same_interests.FindPeopleWithSameInterestsOutput;
import com.know_me.know_me.features.relationship.application.usecases.people.find_people_with_same_interests.FindPeopleWithSameInterestsUseCase;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.features.relationship.entrypoints.http.dtos.response.FindPeopleWithSameInterestsResponseDto;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import com.know_me.know_me.shared.entrypoints.http.dtos.response.SuccessResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final FindPeopleWithSameInterestsUseCase findPeopleWithSameInterestsUseCase;

    public PeopleController(FindPeopleWithSameInterestsUseCase findPeopleWithSameInterestsUseCase) {
        this.findPeopleWithSameInterestsUseCase = findPeopleWithSameInterestsUseCase;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{peopleId}/interests/match", produces = "application/json")
    public SuccessResponseDto<List<FindPeopleWithSameInterestsResponseDto>> findPeopleWithSameInterests(@PathVariable ID peopleId) {
        List<FindPeopleWithSameInterestsOutput> peoples = findPeopleWithSameInterestsUseCase.execute(peopleId);
        return new SuccessResponseDto<>(peoples.stream().map(
                        people -> new FindPeopleWithSameInterestsResponseDto(
                                people.id().value().toString(),
                                people.name(),
                                people.interests().stream().map(Interest::name).collect(Collectors.toSet())
                        )).toList());
    }
}
