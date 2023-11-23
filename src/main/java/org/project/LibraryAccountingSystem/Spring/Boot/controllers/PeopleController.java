package org.project.LibraryAccountingSystem.Spring.Boot.controllers;


import jakarta.validation.Valid;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.People;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }


    @GetMapping
    public String allPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "/people/allPeople";
    }

    @GetMapping("/{id}")
    public String people(@PathVariable("id") int id, Model model1) {
        model1.addAttribute("people", peopleService.findById(id));
        if (peopleService.reedBooks(id))
            model1.addAttribute("books", peopleService.getBookForPeople(id));
        else
            model1.addAttribute("books", new ArrayList<Book>());

        return "/people/people";
    }

    @GetMapping("/new")
    public String newPeople(Model model) {
        model.addAttribute("people", new People());
        return "/people/addPeople";
    }

    @PostMapping()
    public String createNewPeople(@ModelAttribute("people") @Valid People people, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/people/addPeople";
        peopleService.savePeople(people);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") int id) {

        model.addAttribute("people", peopleService.findById(id));
        return "/people/editPeople";
    }

    @PatchMapping("{id}")
    public String edit(@ModelAttribute("people") @Valid People people
            , BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "/people/editPeople";

        peopleService.editPeople(id, people);
        return "redirect:/people/" + id;
    }
}
