package org.project.LibraryAccountingSystem.Spring.Boot.services;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import org.project.LibraryAccountingSystem.Spring.Boot.config.HttpSessionConfig;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.PersonRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepositories personRepositories;
    private final HttpSessionConfig httpSessionConfig;
    private final PasswordEncoder passwordEncoder ;

    @Autowired
    public PersonService(PersonRepositories personRepositories, HttpSessionConfig httpSessionConfig, PasswordEncoder passwordEncoder) {
        this.personRepositories = personRepositories;
        this.httpSessionConfig = httpSessionConfig;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> findAll() {

        List<Person> personList = personRepositories.findAll();
        List<HttpSession> httpSessionList = httpSessionConfig.getActiveSessions();
        for (Person person : personList) {
            for (HttpSession session : httpSessionList) {
                Person person1 = findById((int) session.getAttribute("principal"));
                if (person.getId() == person1.getId()) {
                    person.setAuth(true);
                }
            }

        }
        return personList;
    }
    public List<Person> findAllRoleUser(){
        return personRepositories.findAllByRole("ROLE_USER");
    }

    public Person findById(int id) {
        Optional<Person> person = personRepositories.findById(id);
        if (person.isEmpty()) throw new UsernameNotFoundException("User not found");
        else return person.get();
    }

    @Transactional
    public void delete(int id) {
        logout(id);
        personRepositories.deleteById(id);

    }

    @Transactional
    void save(Person person) {
        personRepositories.save(person);
    }

    @Transactional
    public void edit(Person person, int id) {
        person.setId(id);
        personRepositories.save(person);
    }
    @Transactional
    public void editRole(Person person , int id ){
        Person existingPerson = personRepositories.findById(id).orElse(null);
        if (existingPerson != null) {
            existingPerson.setRole(person.getRole());
        } else {
            // Обработка ситуации, когда пользователь не найден
        }
    }
    public boolean reedBooks(int id){
        return !personRepositories.findById(id).orElse(new Person()).getBooksPeople().isEmpty();
    }


    public void logout(int id) {
        List<HttpSession> httpSessionList = httpSessionConfig.getActiveSessions();

        for (HttpSession session : httpSessionList) {

            int id1 = findById((int) session.getAttribute("principal")).getId();

            if (id1 == id) {
                session.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
                httpSessionConfig.httpSessionListener().sessionDestroyed(new HttpSessionEvent(session));
                personRepositories.findById(id).orElse(new Person()).setAuth(false);
            }
        }
    }


    public Object getBookForPeople(int id) {
        Optional<Person> people = personRepositories.findById(id);
        if (people.isPresent()) {
            long millisecondsToDay = 86400000;
            people.get().getBooksPeople().forEach(a -> a.setOverdue(
                    (((new Date()).getTime() - a.getDate_taken().getTime()) / millisecondsToDay) >= 10));

            return people.get().getBooksPeople();
        } else return Collections.emptyList();
    }
    @Transactional
    public void updatePassword (String password , int id ){
        Person person = personRepositories.findById(id).orElse(null);
        person.setPassword(passwordEncoder.encode(password));
    }

}
