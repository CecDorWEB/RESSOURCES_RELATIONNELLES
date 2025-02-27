package com.RESSOURCES_RELATIONNELLES.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/moderator")
	public String moderatorHome() {
		return "moderator";
	}

	@GetMapping("/administrator")
	public String administratorHome() {
		return "administrator";
	}

	@GetMapping("/superAdministrator")
	public String superAdministratorHome() {
		return "superAdministrator";
	}

}
