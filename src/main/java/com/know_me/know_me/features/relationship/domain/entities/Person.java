package com.know_me.know_me.features.relationship.domain.entities;

import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.shared.domain.valueobjects.ID;

import java.util.HashSet;
import java.util.Set;

public class Person {
    public final ID id;
    public final String name;
    private final Set<Interest> _interests = new HashSet<>();
    private final Set<Person> _friends = new HashSet<>();

    public Person(ID id, String name, Set<String> interests, Set<Person> friends) {
        this.id = id;
        this.name = name;
        interests.forEach(this::addInterest);
        friends.forEach(this::addFriend);
    }

    public Set<Person> getFriends() {
        return _friends;
    }

    public Set<Interest> getInterests() {
        return _interests;
    }

    public void addInterest(String interestName) {
        Interest interest = new Interest(interestName);
        boolean isAlreadyInterest = _interests.stream().anyMatch(interest1 -> interest1.equals(interest));
        if (isAlreadyInterest) {
            throw new IllegalArgumentException("Pessoa já possui esse interesse");
        }
        _interests.add(interest);
    }

    public void removeInterest(String interestName) {
        Interest interest = new Interest(interestName);
        boolean isAlreadyInterest = _interests.stream().anyMatch(i -> i.value().equals(interest.value()));
        if (!isAlreadyInterest) {
            throw new IllegalArgumentException("Pessoa não possui esse interesse");
        }
        _interests.removeIf(i -> i.value().equals(interest.value()));
    }

    public void addFriend(Person friend) {
        if (friend == null) {
            throw new IllegalArgumentException("Amigo não pode ser nulo");
        }

        if (friend.id.equals(id)) {
            throw new IllegalArgumentException("Você não pode ser amigo de si mesmo");
        }

        boolean isAlreadyFriend = _friends.stream().anyMatch(friendPerson -> friendPerson.id.equals(friend.id));
        if (isAlreadyFriend) {
            throw new IllegalArgumentException("Pessoa já possui esse amigo");
        }

        _friends.add(friend);
    }

    public void removeFriend(Person friend) {
        if (friend == null) {
            throw new IllegalArgumentException("Amigo não pode ser nulo");
        }

        boolean isAlreadyFriend = _friends.stream().anyMatch(friendPerson -> friendPerson.id.equals(friend.id));
        if (!isAlreadyFriend) {
            throw new IllegalArgumentException("Pessoa não possui esse amigo");
        }
        _friends.removeIf(f -> f.id.value().equals(friend.id.value()));
    }
}
