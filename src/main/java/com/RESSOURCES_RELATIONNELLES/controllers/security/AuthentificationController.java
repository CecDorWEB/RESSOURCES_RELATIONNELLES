package com.RESSOURCES_RELATIONNELLES.controllers.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

@Controller
public class AuthentificationController {

    private final HttpSession session;


    public AuthentificationController(HttpSession session) {
        this.session = session;

    }


}
