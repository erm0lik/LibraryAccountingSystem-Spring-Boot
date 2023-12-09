package org.project.LibraryAccountingSystem.Spring.Boot.repositories;


import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepositories extends JpaRepository<Book, Integer> {
    Book findByNameStartingWithIgnoreCase(String start);
    List<Book>findByOwnerIsNull  ();

    @Query(value = "SELECT get_average_rating_for_book(:book_id)", nativeQuery = true)
    Double getAverageRatingForBook(@Param("book_id") int book_id);

    @Procedure("delete_people_for_book")
    void deletePeopleForBook(int book_id);

    @Query(value = "SELECT * FROM find_free_books_for_person(:person_id);" , nativeQuery = true)
    List<Book> findFreeBooksForPerson(@Param("person_id") int person_id ) ;

}
