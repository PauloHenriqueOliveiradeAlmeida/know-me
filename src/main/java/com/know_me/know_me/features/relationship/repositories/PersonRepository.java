package com.know_me.know_me.features.relationship.repositories;

import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.shared.domain.valueobjects.ID;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    void save(Person person);
    void addFriend(ID personId, ID friendId);
}
