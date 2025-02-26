package com.RESSOURCES_RELATIONNELLES.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotAccessController {

    @GetMapping("/notAccess")
    public String notAccess() {
        return "notAccess";
    }
}
