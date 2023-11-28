package org.project.LibraryAccountingSystem.Spring.Boot.services;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.BookRequest;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.BookRequestRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookRequestService {
    private final BookRequestRepositories bookRequestRepositories;

    @Autowired
    public BookRequestService(BookRequestRepositories bookRequestRepositories) {
        this.bookRequestRepositories = bookRequestRepositories;
    }

    public List<BookRequest> findAll() {
        return bookRequestRepositories.findAll();
    }

    public BookRequest findById(int id) {
       return bookRequestRepositories.findById(id).orElse(null);
    }
    public List<BookRequest> findAllByOwner(Person person){
        return bookRequestRepositories.findAllByOwnerRequest(person);
    }
    @Transactional
    public void save(BookRequest bookRequest){
        bookRequest.setDate(new Date());
        bookRequestRepositories.save(bookRequest);
    }
    @Transactional
    public void delete(int id) {
        bookRequestRepositories.deleteById(id);
    }



}
