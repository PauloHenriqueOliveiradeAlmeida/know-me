package com.know_me.know_me.features.relationship.application.usecases.person.input;

import java.util.Set;

public record CreatePersonInput (String name, Set<String> interests){ }
