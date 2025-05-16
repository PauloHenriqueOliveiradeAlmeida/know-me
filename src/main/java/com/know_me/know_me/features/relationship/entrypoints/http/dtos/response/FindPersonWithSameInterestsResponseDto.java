package com.know_me.know_me.features.relationship.entrypoints.http.dtos.response;

import java.util.Set;

public record FindPeopleWithSameInterestsResponseDto (String id, String name, Set<String> interests) {}
