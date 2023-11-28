package org.project.LibraryAccountingSystem.Spring.Boot.repositories;

import org.project.LibraryAccountingSystem.Spring.Boot.models.BookRequest;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRequestRepositories extends JpaRepository<BookRequest , Integer> {
    List<BookRequest> findAllByOwnerRequest(Person person) ;
}
