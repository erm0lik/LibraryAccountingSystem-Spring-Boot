package org.project.LibraryAccountingSystem.Spring.Boot.controllers;

import org.project.LibraryAccountingSystem.Spring.Boot.models.BookRequest;
import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookRequestService;
import org.project.LibraryAccountingSystem.Spring.Boot.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/requestBook")
public class RequestsBookController {
    private final BookRequestService bookRequestService;
    private final BookService bookService;

    @Autowired
    public RequestsBookController(BookRequestService bookRequestService, BookService bookService) {
        this.bookRequestService = bookRequestService;
        this.bookService = bookService;
    }

    @GetMapping("/allRequest")
    public String getAllRequestPage(Model model) {
        model.addAttribute("listRequest", bookRequestService.findAll());
        return "bookRequest/allRequest";

    }

    @PostMapping("/approveRequest")
    public String approvRequest(@ModelAttribute("idRequest") int id, Authentication authentication) {
        BookRequest approve = bookRequestService.findById(id);
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        bookService.setPeopleForBook(approve.getOwner().getId(),
                approve.getBook().getBooks_id(),
                personDetails.getPerson().getId());
        bookRequestService.delete(id);
        return "redirect:/requestBook/allRequest";
    }

}
