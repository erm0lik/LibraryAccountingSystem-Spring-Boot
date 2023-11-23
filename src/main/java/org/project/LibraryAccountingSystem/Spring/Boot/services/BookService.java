package org.project.LibraryAccountingSystem.Spring.Boot.services;


import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.BookRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class BookService {
    private final BookRepositories bookRepositories;
    private final PeopleService peopleService;
    private final PersonService personService ;

    @Autowired
    public BookService(BookRepositories bookRepositories, PeopleService peopleService, PersonService personService) {
        this.bookRepositories = bookRepositories;
        this.peopleService = peopleService;
        this.personService = personService;
    }

    @Transactional
    public boolean inUse(int id) {
        return bookRepositories.findById(id).orElse(new Book()).getOwner() != null;
    }

    public List<Book> findAll() {
        return bookRepositories.findAll();
    }

    public List<Book> findAll(int page, int size) {
        return bookRepositories.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<Book> findAll(String sort) {
        return bookRepositories.findAll(Sort.by(sort));
    }

    public List<Book> findAll(int page, int size, String sort) {
        return bookRepositories.findAll(PageRequest.of(page, size, Sort.by(sort))).getContent();
    }

    public Book findById(int id) {
        return bookRepositories.findById(id).orElse(null);
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepositories.save(book);
    }

    @Transactional
    public void editBook(Book book, int id) {
        book.setBooks_id(id);
        bookRepositories.save(book);
    }

    @Transactional
    public void deleteBook(int id) {
        bookRepositories.deleteById(id);
    }

    @Transactional
    public void setPeopleForBook(int people_id, int books_id, int person_id) {
        Book book = findById(books_id);
        book.setOwner(peopleService.findById(people_id));
        book.setLibrarian(personService.findById(person_id));
        book.setDate_taken(new Date());


    }

    @Transactional
    public void deletePeopleForBook(int books_id) {
        Book book = findById(books_id);
        book.setOwner(null);
        book.setLibrarian(null);

    }

    public Book findByStartingWith(String start) {
        if (start.isEmpty()) return null;

        return bookRepositories.findByNameStartingWithIgnoreCase(start);
    }


}
