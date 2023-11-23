package org.project.LibraryAccountingSystem.Spring.Boot.Util;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;
    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
     Person person = (Person) target ;
     try {
        personDetailsService.loadUserByUsername(person.getUserName());
     }
     catch (UsernameNotFoundException ignored){
         return;
     }
     errors.rejectValue("userName" , "" , "This username is already being used by someone");
    }
}
