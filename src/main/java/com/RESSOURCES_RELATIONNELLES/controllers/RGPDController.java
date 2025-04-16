package com.RESSOURCES_RELATIONNELLES.controllers;

import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class RGPDController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/account/delete-request")
    public String showDeletePage(Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        return "account/delete-request";
    }

    @PostMapping("/account/delete-request")
    public String confirmDeletion(@RequestParam("confirmation") String confirmation) {
        User user = (User) session.getAttribute("user");
        if (user != null && confirmation.equalsIgnoreCase("OUI")) {
            user.setFirstName("Utilisateur");
            user.setLastName("Supprimé");
            user.setEmail("deleted_" + user.getId() + "@anonymized.com");
            user.setActived(false);
            userRepository.save(user);
            session.invalidate();
            return "redirect:/account/deleted";
        }
        return "redirect:/account/delete-request?error";
    }

    @GetMapping("/account/deleted")
    public String deletedPage() {
        return "account/deleted";
    }
}
