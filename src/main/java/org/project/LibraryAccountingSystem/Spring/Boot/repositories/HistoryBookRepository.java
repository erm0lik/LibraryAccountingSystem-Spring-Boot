package org.project.LibraryAccountingSystem.Spring.Boot.repositories;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.HistoryBook;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface HistoryBookRepository extends JpaRepository<HistoryBook , Integer> {

    Optional<HistoryBook> findByPersonOwnerAndBookAndDateTaken (Person person , Book book , Date dateTaken);
}
