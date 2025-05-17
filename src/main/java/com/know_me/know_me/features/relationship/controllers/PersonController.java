package com.know_me.know_me.features.relationship.controllers;

import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.services.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    public Person createPerson(@RequestParam String name, @RequestBody Set<String> interests) {
        return service.createPerson(name, interests);
    }
}
