package org.project.LibraryAccountingSystem.Spring.Boot.controllers;


import jakarta.validation.Valid;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final PersonService personService;



    @Autowired
    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String getAllBooks(Model model,
                              @RequestParam(value = "sort", required = false) String sort,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "books_per_page", required = false) Integer books_per_page) {


        if (sort != null && page != null && books_per_page != null) {
            model.addAttribute("sort", true);
            model.addAttribute("books_per_page", books_per_page);
            model.addAttribute("page", page);
            model.addAttribute("books", bookService.findAll(page - 1, books_per_page, "yearOfPublication"));
        } else if (sort != null) {
            model.addAttribute("sort", true);
            model.addAttribute("books", bookService.findAll("yearOfPublication"));
        } else if (page != null && books_per_page != null) {
            model.addAttribute("books_per_page", books_per_page.toString());
            model.addAttribute("page", page);
            model.addAttribute("books", bookService.findAll(page - 1, books_per_page));
        } else model.addAttribute("books", bookService.findAll());

        return "/books/allBooks";
    }

    @GetMapping("/{id}")
    public String getBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("people2", new Person());
        if (bookService.inUse(id))
            model.addAttribute("people", bookService.findById(id).getOwner());
        else model.addAttribute("people", personService.findAll());
        return "/books/book";
    }

    @GetMapping("/add")
    public String addBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "/books/addBook";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors())
            return "/books/addBook";

        System.out.println(file.getContentType());
       bookService.saveBook(book , file);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "/books/editBook";
    }

    @PatchMapping("/{id}/edit")
    public String getEdit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        book.setBooks_id(id);
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
                            @ModelAttribute("people2") Person people_id,
                            Authentication authentication) {

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        bookService.setPeopleForBook(people_id.getId(), id,
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
