package org.project.LibraryAccountingSystem.Spring.Boot.repositories;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUserName(String userName);
    List<Person> findAllByRole(String role);
    @Query(value = "SELECT person.* FROM person LEFT JOIN books b on person.id = b.people_id WHERE b.books_id=:IDBOOK " , nativeQuery = true)
    Optional<Person> getPersonForBookId(@Param("IDBOOK") int bookid);
}
