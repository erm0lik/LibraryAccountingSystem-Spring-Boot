package org.project.LibraryAccountingSystem.Spring.Boot.repositories;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepositories extends JpaRepository<Person, Integer> {
    Optional<Person> findByUserName(String userName);

}
