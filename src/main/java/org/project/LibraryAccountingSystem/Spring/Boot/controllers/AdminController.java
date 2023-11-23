package org.project.LibraryAccountingSystem.Spring.Boot.controllers;

import jakarta.servlet.http.HttpSession;
import org.project.LibraryAccountingSystem.Spring.Boot.config.HttpSessionConfig;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping(("/admin"))
public class AdminController {
    private final PersonService personService;


    @Autowired
    public AdminController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/allPerson")
    public String pageAll(Model model) {
        model.addAttribute("listPerson", personService.findAll());
        return "/admin/allUsers";
    }

    @DeleteMapping("/deletePerson")
    public String deletePerson(@ModelAttribute("id") Person person) {
        personService.delete(person.getId());
        return "redirect:/admin/allPerson";
    }

    @PostMapping("/logoutPerson")
    public String logoutPerson(@ModelAttribute("id") Person person) {
        personService.logout(person.getId());
        return "redirect:/admin/allPerson";
    }
}
