package com.know_me.know_me.features.relationship.application.usecases.person.output;

import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.shared.domain.valueobjects.ID;

import java.util.Set;

public record PersonOutput(ID id, String name, Set<Interest> interests, Set<ID> friends) { }
