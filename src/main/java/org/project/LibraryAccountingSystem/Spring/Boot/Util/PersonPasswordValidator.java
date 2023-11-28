package org.project.LibraryAccountingSystem.Spring.Boot.Util;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component

public class PersonPasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(person.getPassword().length()<=20&&person.getPassword().length()>=2){
            return;
        }
        else errors.rejectValue("password" , "" , "Please enter valid password(size 2-20)");
    }
}
