package org.project.LibraryAccountingSystem.Spring.Boot.repositories;


import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositories extends JpaRepository<Book, Integer> {
    Book findByNameStartingWithIgnoreCase(String start);
    List<Book>findByOwnerIsNull  ();
}
