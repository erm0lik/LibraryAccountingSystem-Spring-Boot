package org.project.LibraryAccountingSystem.Spring.Boot.controllers;

import jakarta.validation.Valid;
import org.project.LibraryAccountingSystem.Spring.Boot.Util.PersonPasswordValidator;
import org.project.LibraryAccountingSystem.Spring.Boot.models.BookRequest;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Review;
import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookRequestService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.PersonService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final PersonService personService;
    private final BookService bookService;
    private final BookRequestService bookRequestService;
    private final PersonPasswordValidator personPasswordValidator;
    private final ReviewService reviewService;
    private static final String USER_MY_BOOK_URL= "redirect:/user/myBooks";

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    public UserController(PersonService personService, BookService bookService, BookRequestService bookRequestService, PersonPasswordValidator personPasswordValidator, ReviewService reviewService) {
        this.personService = personService;
        this.bookService = bookService;
        this.bookRequestService = bookRequestService;
        this.personPasswordValidator = personPasswordValidator;

        this.reviewService = reviewService;
    }

    @ModelAttribute("user")
    public Person getUser(Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

    @GetMapping
    public String getUserPage(Model model , Authentication authentication ) {
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        model.addAttribute("listHistory" , personService.historyBookList(personDetails.getPerson()));
        return "/user/userPage";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute("user") @Valid Person person,
                                 BindingResult bindingResult) {
        personPasswordValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/user/userPage";
        }
        personService.updatePassword(person.getPassword(), person.getId());
        return "redirect:/user";
    }

    @GetMapping("/myBooks")
    public String getMyBooksPage(Model model, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("books", personService.getBookForPeople(personDetails.getPerson().getId()));
        return "/user/myBooks";
    }

    @GetMapping("/myBooks/{id}")
    public String getBookPage(@PathVariable("id") int id, Model model, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (personService.findOwnerForBooksId(id) == null) return USER_MY_BOOK_URL;
        else if (personService.findOwnerForBooksId(id).getId() == personDetails.getPerson().getId()) {
            model.addAttribute("book", bookService.findById(id));
            model.addAttribute("averageStars", bookService.getAverageRatingForBook(id));
            model.addAttribute("listReview", reviewService.findAllForBook(id));
            return "/user/infoMyBook";
        }
        return USER_MY_BOOK_URL;

    }

    @PostMapping("/myBooks/return")
    public String returnBook(@ModelAttribute("id") int id) {
        bookService.deletePeopleForBook(id);
        return USER_MY_BOOK_URL;
    }

    @GetMapping("/myBooks/addReview")
    public String getPageReview(@ModelAttribute("id") int id, Model model) {
        model.addAttribute("isValid", true);
        return "/user/addReview";
    }

    @PostMapping("/myBooks/return/addReview")
    public String addReview(@ModelAttribute("idbook") int id, Authentication authentication, @ModelAttribute("review") Review review, Model model) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (reviewService.save(review, personDetails.getPerson(), bookService.findById(id))) {
            return USER_MY_BOOK_URL;
        } else {
            model.addAttribute("isValid", false);
            return "/user/addReview";
        }
    }

    @GetMapping("/freeBooks")
    public String getFreeBooksPage(Model model, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("freeBooks", bookService.findFreeBook(personDetails.getPerson().getId()));
        return "/user/freeBooks";
    }


    @GetMapping("/myRequests")
    public String getPageRequests(Model model, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("listRequest", bookRequestService.findAllByOwner(personDetails.getPerson()));
        return "/user/userRequest";
    }

    @PostMapping("/addRequest")
    public String addRequest(@ModelAttribute("idBook") int id, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        bookRequestService.save(new BookRequest(personDetails.getPerson(), bookService.findById(id)));
        return "redirect:/user/freeBooks";
    }

    @DeleteMapping("/deleteRequest")
    public String deleteRequest(@ModelAttribute("idRequest") int id) {
        bookRequestService.delete(id);
        return "redirect:/user/myRequests";
    }


}
