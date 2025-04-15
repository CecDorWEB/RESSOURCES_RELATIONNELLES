package com.RESSOURCES_RELATIONNELLES.controllers.security;

import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import com.RESSOURCES_RELATIONNELLES.services.SecurityService;
import com.RESSOURCES_RELATIONNELLES.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthentificationController {

    private final HttpSession session;
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthentificationController(HttpSession session, UserRepository userRepository, UserService userService,SecurityService securityService) {
        this.session = session;
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (this.securityService.isAuthenticated()) {
            return "home";
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/debug/session")
    @ResponseBody
    public String debugSession(HttpSession session) {
        Object token = session.getAttribute("IsUserConnectedToken");
        User user = (User) session.getAttribute("user");

        if (token != null && user != null) {
            return "Connecté en tant que : " + user.getFirstName() + " - Rôle : " + user.getRole().getName();
        } else {
            return "Non connecté ou session vide.";
        }
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        if (this.securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/logout")
    public String logout() {
        this.securityService.removeAuthToken();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) {
        if (this.securityService.isAuthenticated()) {
            return "redirect:/home";
        }
        boolean isAuth = this.securityService.login( user.getEmail() , user.getPassword());
        boolean isBanned = this.securityService.isBanned(user.getEmail());
        if (isAuth && !isBanned) {
            this.securityService.setAuthToken();
            return "redirect:/home"; // Connexion réussie, redirection vers l'accueil
        } else {
            model.addAttribute("error", "Un problème est survenu lors de la connexion veuillez contacter le service utilisateur !");
            return "login"; // Retour à la page login avec un message d'erreur
        }
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model) {
        if (this.securityService.isAuthenticated()) {
            return "redirect:/home";
        }
        boolean signUpOk =  this.securityService.signUpUser(user);
        if (signUpOk) {
            return "redirect:/login"; // Redirige vers login après inscription réussie
        }
        else {
            model.addAttribute("error", "Problème lors de l'inscription !");
            return "signup"; // Retourne la page signup avec le message d'erreur
        }
    }
}
