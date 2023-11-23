package org.project.LibraryAccountingSystem.Spring.Boot.controllers;


import jakarta.validation.Valid;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.People;
import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getAllBooks(Model model,
                              @RequestParam(value = "sort", required = false) String sort,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "books_per_page", required = false) Integer books_per_page) {

        System.out.println(sort);
        if (sort != null && page != null & books_per_page != null) {
            model.addAttribute("sort", true);
            model.addAttribute("books_per_page", books_per_page);
            model.addAttribute("page", page);
            model.addAttribute("books", bookService.findAll(page - 1, books_per_page, "yearOfPublication"));
        } else if (sort != null) {
            model.addAttribute("sort", true);
            model.addAttribute("books", bookService.findAll("yearOfPublication"));
        } else if (page != null & books_per_page != null) {
            model.addAttribute("books_per_page", books_per_page.toString());
            model.addAttribute("page", page);
            model.addAttribute("books", bookService.findAll(page - 1, books_per_page));
        } else model.addAttribute("books", bookService.findAll());

        return "/books/allBooks";
    }

    @GetMapping("/{id}")
    public String getBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("people2", new People());
        if (bookService.inUse(id))
            model.addAttribute("people", bookService.findById(id).getOwner());
        else model.addAttribute("people", peopleService.findAll());
        return "/books/book";
    }

    @GetMapping("/add")
    public String addBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "/books/addBook";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/books/addBook";

        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "/books/editBook";
    }

    @PatchMapping("/{id}/edit/")
    public String getEdit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "/books/editBook";

        bookService.editBook(book, id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String deletePeople(@PathVariable("id") int id) {
        bookService.deletePeopleForBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/setPeople")
    public String setPeople(@PathVariable("id") int id,
                            @ModelAttribute("people2") People people_id,
                            Authentication authentication) {

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        bookService.setPeopleForBook(people_id.getPeople_id(), id,
                personDetails.getPerson().getId());
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "start", required = false) String start, Model model) {
        if (start != null) {
            model.addAttribute("book", bookService.findByStartingWith(start));
        } else model.addAttribute("book", null);
        return "/books/search";
    }


}
