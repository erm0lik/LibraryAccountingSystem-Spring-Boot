package org.project.LibraryAccountingSystem.Spring.Boot.repositories;


import org.project.LibraryAccountingSystem.Spring.Boot.models.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepositories extends JpaRepository<People, Integer> {
}
