package com.know_me.know_me.features.relationship.domain.entities;

import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.shared.domain.valueobjects.ID;

import java.util.HashSet;
import java.util.Set;

public class Person {
    public final ID id;
    public final String name;
    private Set<Interest> _interests;
    private Set<Person> _friends;

    public Person(ID id, String name, Set<Interest> interests, Set<Person> friends) {
        this.id = id;
        this.name = name;
        this._interests = new HashSet<>();
        this._friends = new HashSet<>();
        interests.forEach(this::addInterest);
        friends.forEach(this::addFriend);
    }

    public Set<Person> getFriends() {
        return _friends;
    }

    public Set<Interest> getInterests() {
        return _interests;
    }

    public void addInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Interest cannot be null");
        }
        _interests.add(interest);
    }

    public void addFriend(Person friend) {
        if (friend == null) {
            throw new IllegalArgumentException("Friend cannot be null");
        }

        if (friend.equals(this)) {
            throw new IllegalArgumentException("You cannot be your friend");
        }

        _friends.add(friend);
    }
}
