package org.project.LibraryAccountingSystem.Spring.Boot.services;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.PersonRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final PersonRepositories personRepositories;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepositories personRepositories, PasswordEncoder passwordEncoder) {
        this.personRepositories = personRepositories;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_LIBRARIAN");
        personRepositories.save(person);
    }

}
