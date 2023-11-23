package org.project.LibraryAccountingSystem.Spring.Boot.controllers;

import org.project.LibraryAccountingSystem.Spring.Boot.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping("/menu")
    public String getMenu(Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (personDetails.getPerson().getRole().equals("ROLE_ADMIN"))
            return "redirect:/menu-admin";
        return "/menu/menu";
    }

    @GetMapping("/menu-admin")
    public String getAdminMenu() {
        return "/menu/menuAdmin";
    }

}
