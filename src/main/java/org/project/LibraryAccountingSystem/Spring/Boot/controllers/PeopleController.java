package org.project.LibraryAccountingSystem.Spring.Boot.controllers;



import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;

    @Autowired
    public PeopleController(PersonService personService) {
        this.personService = personService;

    }

    @GetMapping
    public String allPeople(Model model) {
        model.addAttribute("people", personService.findAllRoleUser());
        return "/people/allPeople";
    }

    @GetMapping("/{id}")
    public String people(@PathVariable("id") int id, Model model1) {
        model1.addAttribute("people", personService.findById(id));
        if (personService.reedBooks(id))
            model1.addAttribute("books", personService.getBookForPeople(id));
        else
            model1.addAttribute("books", new ArrayList<Book>());

        return "/people/people";
    }

    }

