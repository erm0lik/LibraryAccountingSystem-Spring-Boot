package org.project.LibraryAccountingSystem.Spring.Boot.services;


import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.People;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.PeopleRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
@Service
public class PeopleService {
    private final PeopleRepositories peopleRepositories;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }

    public List<People> findAll() {
        return peopleRepositories.findAll();
    }

    public People findById(int id) {
        Optional<People> optionalPeople = peopleRepositories.findById(id);
        return optionalPeople.orElse(null);
    }

    @Transactional
    public void savePeople(People people) {
        peopleRepositories.save(people);
    }

    @Transactional
    public void editPeople(int id, People updatePeople) {
        updatePeople.setPeople_id(id);
        peopleRepositories.save(updatePeople);
    }

    @Transactional
    public void delete(int id) {
        peopleRepositories.deleteById(id);
    }

    public boolean reedBooks(int id) {
        return !peopleRepositories.findById(id).orElse(new People()).getBooks().isEmpty();
    }

    public List<Book> getBookForPeople(int id) {
        Optional<People> people = peopleRepositories.findById(id);
        if (people.isPresent()) {
            long millisecondsToDay = 86400000;
            people.get().getBooks().forEach(a -> a.setOverdue(
                    (((new Date()).getTime() - a.getDate_taken().getTime()) / millisecondsToDay) >= 10));

            return people.get().getBooks();
        } else return Collections.emptyList();
    }

}
