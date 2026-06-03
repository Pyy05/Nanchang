package com.team.user_admin_system.module.controller;

import com.team.user_admin_system.module.entity.Person;
import com.team.user_admin_system.module.service.impl.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/list")
    public List<Person> getPersons(
            @RequestParam(name = "dynasty", required = false) String dynasty,
            @RequestParam(name = "name", required = false) String name
    ) {
        if (name != null && !name.trim().isEmpty()) {
            return personService.searchByName(name);
        }
        if (dynasty == null || "全部时期".equals(dynasty) || "all".equals(dynasty)) {
            return personService.listAll();
        }
        return personService.listByDynasty(dynasty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personService.getById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
