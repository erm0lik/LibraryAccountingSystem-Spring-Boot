package org.project.LibraryAccountingSystem.Spring.Boot.controllers;

import jakarta.validation.Valid;
import org.project.LibraryAccountingSystem.Spring.Boot.Util.PersonPasswordValidator;
import org.project.LibraryAccountingSystem.Spring.Boot.models.BookRequest;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookRequestService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final PersonService personService ;
    private final BookService bookService;
    private final BookRequestService bookRequestService ;
    private final PersonPasswordValidator personPasswordValidator;
    @Autowired
    public UserController(PersonService personService, BookService bookService, BookRequestService bookRequestService, PersonPasswordValidator personPasswordValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.bookRequestService = bookRequestService;
        this.personPasswordValidator = personPasswordValidator;
    }

    @ModelAttribute("user")
    public Person getUser(Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

    @GetMapping
    public String getUserPage() {
        return "/user/userPage";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute("user") @Valid Person person,
                                 BindingResult bindingResult ){
        personPasswordValidator.validate(person , bindingResult);
        if (bindingResult.hasErrors()) {
            return "/user/userPage";
        }
        personService.updatePassword(person.getPassword(), person.getId());
        return "redirect:/user";
    }
    @GetMapping("/myBooks")
    public String getMyBooksPage(Model model , Authentication authentication){
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal() ;
        model.addAttribute("books" , personService.getBookForPeople(personDetails.getPerson().getId()));
        return "/user/myBooks";
    }

    @PostMapping("/myBooks/turn")
    public String turnBook(@ModelAttribute("id")int id){
        bookService.deletePeopleForBook(id);
        return "redirect:/user/myBooks";
    }

    @GetMapping("/freeBooks")
    public String getFreeBooksPage(Model model , Authentication authentication){
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        model.addAttribute("freeBooks" , bookService.findFreeBook(personDetails.getPerson()));
        return "/user/freeBooks";
    }
    @GetMapping("/freeBooks/{id}")
    public String getBookPage (@PathVariable("id") int id , Model model ){
    model.addAttribute( "book", bookService.findById(id)) ;
    return "/user/infoBook" ;
    }
    @GetMapping("/myRequests")
    public String getPageRequests(Model model , Authentication  authentication ){
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("listRequest" , bookRequestService.findAllByOwner(personDetails.getPerson()));
        return "/user/userRequest";
    }
    @PostMapping("/addRequest")
    public String addRequest(@ModelAttribute("idBook") int id , Authentication  authentication){
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        bookRequestService.save(new BookRequest(personDetails.getPerson() , bookService.findById(id)));
        return "redirect:/user/myRequests" ;
    }
    @DeleteMapping("/deleteRequest")
    public String deleteRequest(@ModelAttribute("idRequest") int id ){
        bookRequestService.delete(id);
        return "redirect:/user/myRequests";
    }


}
