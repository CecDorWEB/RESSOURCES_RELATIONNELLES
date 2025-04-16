package com.RESSOURCES_RELATIONNELLES.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.RESSOURCES_RELATIONNELLES.entities.Comment;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.services.CommentService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;

@Controller
public class UserController {
	@Autowired
	private RessourceService ressourceService;
	@Autowired
	private CommentService commentService;

	@GetMapping("/moderator")
	public String moderatorHome() {
		return "moderator";
	}

	@GetMapping("/moderator/gestion")
	public String moderatorGestionRessourcesAndComments(Model model) {
		List<Ressource> ressources = ressourceService.getAllRessourcesWithStateNullOrFalse();
		List<Comment> comments = commentService.getAllCommentsOnlyTrue();

		// Passer les ressources à la vue
		model.addAttribute("ressources", ressourceService.getAllRessourcesWithStateNullOrFalse());
		model.addAttribute("commentaires", commentService.getAllCommentsOnlyTrue());

		// Retourner la vue de gestion des ressources
		return "moderator";
	}

	@GetMapping("/administrator")
	public String administratorHome() {
		return "administrator";
	}

	@GetMapping("/administrator/gestionRessources")
	public String administraorGestionRessources(Model model) {
		List<Ressource> ressources = ressourceService.getAllRessources();
		model.addAttribute("ressources", ressources);
		return "gestionRessources";
	}

	@GetMapping("/superAdministrator")
	public String superAdministratorHome() {
		return "superAdministrator";
	}

}
