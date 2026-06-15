package com.team.user_admin_system.module.service;

import com.team.user_admin_system.module.entity.Person;
import java.util.List;

public interface PersonService {
    List<Person> listAll();
    List<Person> listByDynasty(String dynasty);
    List<Person> searchByName(String name);
    Person getById(Long id);
    Person savePerson(Person person);
    void deletePerson(Long id);
}