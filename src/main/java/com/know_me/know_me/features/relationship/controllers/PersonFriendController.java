package com.know_me.know_me.features.relationship.controllers;

import com.know_me.know_me.features.relationship.services.PersonService;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/people")
public class PersonFriendController {

    private final PersonService service;

    public PersonFriendController(PersonService service) {
        this.service = service;
    }

    @PostMapping("/{personId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable String personId, @PathVariable String friendId) {
        service.addFriend(ID.fromString(personId), ID.fromString(friendId));
        return ResponseEntity.ok().build();
    }
}
