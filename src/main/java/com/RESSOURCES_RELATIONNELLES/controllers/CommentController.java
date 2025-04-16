package com.RESSOURCES_RELATIONNELLES.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.RESSOURCES_RELATIONNELLES.entities.Comment;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import com.RESSOURCES_RELATIONNELLES.services.CommentService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;
import com.RESSOURCES_RELATIONNELLES.services.SecurityService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private RessourceService ressourceService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SecurityService securityService;

	// Ajouter un commentaire à une ressource
	@GetMapping("/ajouter_commentaire/{ressourceId}")
	public String ajouterCommentaire(@PathVariable Long ressourceId, @RequestParam("content") String content) {

		Optional<Ressource> ressourceOptional = ressourceService.findById(ressourceId);
		if (!ressourceOptional.isPresent()) {
			return "redirect:/ressource";
		}

		Ressource ressource = ressourceOptional.get();

		if (securityService.isAuthenticated()) {
			User currentUser = securityService.getCurrentUser();

			if (currentUser != null) {
				System.out.println("👤 Utilisateur connecté : " + currentUser.getEmail());

				// ✅ Créer et configurer le commentaire
				Comment newComment = new Comment();
				newComment.setName(currentUser.getFirstName());

				newComment.setContent(content);
				newComment.setActivated(true);
				newComment.setReported(false);
				newComment.setRessource(ressource);

				commentService.save(newComment);
			} else {
				System.out.println("❌ currentUser est null même si authenticated !");
			}
		} else {
			System.out.println("🚫 Utilisateur non connecté");
		}

		// Redirection vers la page de la ressource, que l'user soit connecté ou pas
		return "redirect:/ressource/" + ressourceId;
	}

	@GetMapping("/supprimer_commentaire/{ressourceId}/{commentId}")
	public String supprimerCommentaire(@PathVariable Long ressourceId, @PathVariable Long commentId) {
		commentService.deleteCommentById(commentId);
		return "redirect:/moderator/gestion"; // Ou retourne vers la ressource concernée, si tu préfères
	}

}
