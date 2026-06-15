package com.team.user_admin_system.module.service.impl;

import com.team.user_admin_system.module.entity.Person;
import com.team.user_admin_system.module.repository.PersonRepository;
import com.team.user_admin_system.module.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> listAll() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> listByDynasty(String dynasty) {
        return personRepository.findByDynasty(dynasty);
    }

    @Override
    public List<Person> searchByName(String name) {
        return personRepository.findByNameContaining(name);
    }

    @Override
    public Person getById(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
