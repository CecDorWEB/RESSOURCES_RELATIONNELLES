package com.RESSOURCES_RELATIONNELLES.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.RESSOURCES_RELATIONNELLES.entities.Comment;
import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.RessourceType;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import com.RESSOURCES_RELATIONNELLES.services.CommentService;
import com.RESSOURCES_RELATIONNELLES.services.RelationTypeService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceTypeService;
import com.RESSOURCES_RELATIONNELLES.services.SecurityService;

import jakarta.validation.Valid;

@Controller
public class RessourceController {

	private final RessourceService _ressourceService;
	private final RelationTypeService _relationTypeService;
	private final RessourceTypeService _ressourceTypeService;
	private final CommentService _commentService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SecurityService securityService;

	@Autowired
	public RessourceController(RessourceService ressourceService, RelationTypeService relationTypeService,
			RessourceTypeService ressourceTypeService, CommentService commentService) {
		this._ressourceService = ressourceService;
		this._relationTypeService = relationTypeService;
		this._ressourceTypeService = ressourceTypeService;
		this._commentService = commentService; // Initialiser le service des commentaires
	}

	@GetMapping("/ressources/getall")
	public List<Ressource> getAllRessources() {
		return _ressourceService.getAllRessources();
	}

	@GetMapping("/ressource/create")
	public String openCreateForm(Model model) {
		model.addAttribute("title", "Création d'une ressource");
		model.addAttribute("ressource", new Ressource());
		return "ressourceForm";
	}

	// Récupération des ressources PUBLIQUES pour les utilisateurs non connectés
	@GetMapping("/ressources")
	public String consultAllRessources(Model model, @RequestParam(required = false) Long relationTypeId,
			@RequestParam(required = false) Long ressourceTypeId, @RequestParam(required = false) String searchWord) {
		List<RelationType> relationType = _relationTypeService.getAllRelationType();
		List<RessourceType> ressourceType = _ressourceTypeService.getAllRessourceType();

		List<Ressource> ressource;

		if (relationTypeId != null || ressourceTypeId != null || searchWord != null) {
			ressource = _ressourceService.getPublicFilteredRessources(relationTypeId, ressourceTypeId, searchWord);
		} else {
			ressource = _ressourceService.getAllPublicRessources();
		}

		model.addAttribute("listRelation", relationType);
		model.addAttribute("listRessourceType", ressourceType);
		model.addAttribute("listRessource", ressource);
		return "listRessource";
	}

	// Récupérer le contenu de la ressource par son id
	@GetMapping("/ressource/{id}")
	public String afficherRessource(@PathVariable Long id, Model model) {

		System.out.println("🔍 ID demandé : " + id);
		Optional<Ressource> ressource = _ressourceService.findById(id);
		System.out.println("➡️ Ressource trouvée ? " + ressource.isPresent());

		if (ressource.isPresent()) {
			List<Comment> comments = ressource.get().getComments();
			model.addAttribute("ressource", ressource.get());
			model.addAttribute("comments", comments);

			if (securityService.isAuthenticated()) {
				User currentUser = securityService.getCurrentUser();
				model.addAttribute("currentUser", currentUser);
			}

			return "ressource";
		} else {
			return "redirect:/home";
		}
	}

	@GetMapping("/ressource/edit/{id}")
	public String openEditForm(@PathVariable Long id, Model model) {
		Optional<Ressource> ressource = _ressourceService.findById(id);
		if (ressource.isPresent()) {
			List<String> paragraphs = extractParagraphs(ressource.get().getContent());
			model.addAttribute("title", "Modification d'une ressource");
			model.addAttribute("paragraphs", paragraphs);
			model.addAttribute("ressource", ressource.get());
			return "ressourceForm"; // Formulaire de modification
		}
		return "redirect:/home"; // Redirige si l'ID n'existe pas
	}

	@PostMapping("/saveRessource")
	public String saveOrUpdateRessource(@Valid Ressource ressource, BindingResult result,
			@RequestParam List<String> paragraphs, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("title",
					ressource.getId() == null ? "Création d'une ressource" : "Modification d'une ressource");
			return "ressourceForm";
		}

		// Transformation des paragraphes en <section>
		String content = paragraphs.stream().filter(p -> !p.trim().isEmpty()).map(p -> "<section>" + p + "</section>")
				.collect(Collectors.joining(""));

		ressource.setContent(content);

		var newRessource = _ressourceService.save(ressource);

		if (newRessource != null && newRessource.getId() > 0) {
			return "redirect:/ressource/" + newRessource.getId();
		} else {
			return "redirect:/home";
		}
	}

	private List<String> extractParagraphs(String content) {
		if (content == null || content.isBlank()) {
			return List.of();
		}

		return Arrays.stream(content.split("</section>")) // Séparer sur </section>
				.map(p -> p.replace("<section>", "").trim()) // Supprimer les <section> d'ouverture
				.filter(p -> !p.isEmpty()) // Enlever les vides
				.collect(Collectors.toList()); // Convertir en liste
	}

	@GetMapping("/ressource/delete/{id}")
	public String deleteRessource(@PathVariable Long id, Model model) {
		Optional<Ressource> ressource = _ressourceService.findById(id);

		if (ressource.isPresent()) {
			_ressourceService.DeleteRessource(ressource.get());
		} else {
			model.addAttribute("errorMessage", "Ressource non trouvée");
		}

		return "redirect:/administrator/gestionRessources";
	}

	@PostMapping("/commenter/{ressourceId}")
	public String commenter(@PathVariable Long ressourceId, @RequestParam User user, @RequestParam String content) {

		Optional<Ressource> ressourceOpt = _ressourceService.findById(ressourceId);

		if (ressourceOpt.isPresent()) {
			Ressource ressource = ressourceOpt.get();

			Comment comment = new Comment();
			comment.setName(user.getFirstName());
			comment.setContent(content);
			comment.setRessource(ressource); // si tu as une relation @ManyToOne

			_commentService.save(comment); // ou tout autre moyen de persister le commentaire

			return "redirect:/ressource/" + ressourceId;
		} else {
			return "redirect:/home";
		}
	}

}
