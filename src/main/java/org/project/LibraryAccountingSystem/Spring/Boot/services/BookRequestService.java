package org.project.LibraryAccountingSystem.Spring.Boot.services;

import org.project.LibraryAccountingSystem.Spring.Boot.models.BookRequest;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.BookRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookRequestService {
    private final BookRequestRepository bookRequestRepository;

    @Autowired
    public BookRequestService(BookRequestRepository bookRequestRepository) {
        this.bookRequestRepository = bookRequestRepository;
    }

    public List<BookRequest> findAll() {
        return bookRequestRepository.findAll();
    }

    public BookRequest findById(int id) {
       return bookRequestRepository.findById(id).orElse(null);
    }
    public List<BookRequest> findAllByOwner(Person person){
        return bookRequestRepository.findAllByOwnerRequest(person);
    }
    @Transactional
    public void save(BookRequest bookRequest){
        bookRequest.setDate(new Date());
        bookRequestRepository.save(bookRequest);
    }
    @Transactional
    public void delete(int id) {
        bookRequestRepository.deleteById(id);
    }



}
