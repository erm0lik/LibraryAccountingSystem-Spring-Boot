package org.project.LibraryAccountingSystem.Spring.Boot.services;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.HistoryBook;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.HistoryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class HistoryBookService {
    private final HistoryBookRepository historyBookRepository ;
    @Autowired
    public HistoryBookService(HistoryBookRepository historyBookRepository) {
        this.historyBookRepository = historyBookRepository;
    }
    @Transactional
    public void save (Book book , Person person ){
        HistoryBook historyBook = new HistoryBook() ;
        historyBook.setBook(book);
        historyBook.setPersonOwner(person);
        historyBook.setDateTaken(book.getDate_taken());
        historyBookRepository.save(historyBook);
    }
    @Transactional
    public void update(Person person , Book book , Date date){
        HistoryBook historyBook = historyBookRepository.findByPersonOwnerAndBookAndDateTaken(person , book , date).orElseThrow();
        historyBook.setDateReturned(new Date());
    }
}
