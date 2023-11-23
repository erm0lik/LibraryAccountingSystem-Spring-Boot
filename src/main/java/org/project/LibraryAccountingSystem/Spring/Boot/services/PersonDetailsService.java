package org.project.LibraryAccountingSystem.Spring.Boot.services;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.PersonRepositories;
import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepositories personRepositories;

    @Autowired
    public PersonDetailsService(PersonRepositories personRepositories) {
        this.personRepositories = personRepositories;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepositories.findByUserName(username);
        if (person.isEmpty()) throw new UsernameNotFoundException("User not found");
        else return new PersonDetails(person.get());
    }
}
