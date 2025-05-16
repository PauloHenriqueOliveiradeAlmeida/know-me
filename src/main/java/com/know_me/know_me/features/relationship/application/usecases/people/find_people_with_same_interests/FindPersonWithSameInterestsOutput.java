package com.know_me.know_me.features.relationship.application.usecases.people.find_people_with_same_interests;

import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.shared.domain.valueobjects.ID;

import java.util.Set;

public record FindPeopleWithSameInterestsOutput(ID id, String name, Set<Interest> interests) { }
