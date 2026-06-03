package com.team.user_admin_system.module.repository;

import com.team.user_admin_system.module.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByDynasty(String dynasty);
    List<Person> findByNameContaining(String name);
}
