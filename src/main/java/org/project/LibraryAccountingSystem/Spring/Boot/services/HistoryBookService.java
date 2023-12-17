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


}
