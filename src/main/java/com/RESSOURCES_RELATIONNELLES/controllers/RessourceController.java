package com.RESSOURCES_RELATIONNELLES.controllers;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.RESSOURCES_RELATIONNELLES.entities.Comment;
import com.RESSOURCES_RELATIONNELLES.entities.HaveRelationType;
import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.RessourceType;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import com.RESSOURCES_RELATIONNELLES.services.CategoryService;
import com.RESSOURCES_RELATIONNELLES.services.CommentService;
import com.RESSOURCES_RELATIONNELLES.services.ExploitService;
import com.RESSOURCES_RELATIONNELLES.services.FavoriteService;
import com.RESSOURCES_RELATIONNELLES.services.HaveRelationTypeService;
import com.RESSOURCES_RELATIONNELLES.services.RelationTypeService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceTypeService;
import com.RESSOURCES_RELATIONNELLES.services.SecurityService;
import com.RESSOURCES_RELATIONNELLES.services.saveToConsultService;

import jakarta.servlet.http.HttpSession;
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

	private final CategoryService _categoryService;
	private final HaveRelationTypeService _haveRelationTypeService;
	private final FavoriteService _favoriteService;
	private final ExploitService _exploitService;
	private final saveToConsultService _saveToConsultService;

	public RessourceController(RessourceService ressourceService, RelationTypeService relationTypeService,
			RessourceTypeService ressourceTypeService, CommentService commentService, FavoriteService favoriteService,
			CategoryService categoryService, HaveRelationTypeService haveRelationTypeService,
			ExploitService exploitService, saveToConsultService saveToConsultService) {
		this._ressourceService = ressourceService;
		this._relationTypeService = relationTypeService;
		this._ressourceTypeService = ressourceTypeService;
		this._commentService = commentService; // Initialiser le service des commentaires
		this._favoriteService = favoriteService;
		this._categoryService = categoryService;
		this._haveRelationTypeService = haveRelationTypeService;
		this._exploitService = exploitService;
		this._saveToConsultService = saveToConsultService;
	}

	@GetMapping("/ressources/getall")
	public List<Ressource> getAllRessources() {
		return _ressourceService.getAllRessources();
	}

	@GetMapping("/ressource/create")
	public String openCreateForm(Model model) {
		model.addAttribute("title", "Création d'une ressource");
		model.addAttribute("ressource", new Ressource());
		model.addAttribute("relationTypes", _relationTypeService.findAll());
		model.addAttribute("ressourceTypes", _ressourceTypeService.findAll());
		model.addAttribute("categories", _categoryService.findAll());

		return "ressourceForm";
	}

	// Récupération des ressources PUBLIC pour les USERS non connectés, et PRIVE
	// pour les users connectés publiées et autorisées pour alimenter la liste des
	// ressources
	@GetMapping("/ressources")
	public String consultAllRessources(Model model, @RequestParam(required = false) Long relationTypeId,
			@RequestParam(required = false) Long ressourceTypeId, @RequestParam(required = false) String searchWord,
			HttpSession session) {

		User user = (User) session.getAttribute("user");

		List<RelationType> relationType = _relationTypeService.findAll();
		List<RessourceType> ressourceType = _ressourceTypeService.findAll();

		List<Ressource> ressource;
		if (user == null) {
			if (relationTypeId != null || ressourceTypeId != null || searchWord != null) {
				ressource = _ressourceService.getPublicFilteredRessources(relationTypeId, ressourceTypeId, searchWord);
			} else {
				ressource = _ressourceService.getAllPublicRessources();
			}
		} else {
			Long userId = user.getId();
			if (relationTypeId != null || ressourceTypeId != null || searchWord != null) {
				ressource = _ressourceService.getFilteredRessources(relationTypeId, ressourceTypeId, searchWord,
						userId);
			} else {
				ressource = _ressourceService.getAllRessourcesForConnectedUSer(userId);
			}
		}

		// Si user connecté je récupère la session, les favoris, les ressources
		// exploitées
		if (user != null) {
			model.addAttribute("myUser", user);

			Set<Long> favoriteIds = _favoriteService.getFavoriteIdByUserId(user.getId());
			model.addAttribute("favoriteIds", favoriteIds);
		}

		model.addAttribute("listRelation", relationType);
		model.addAttribute("listRessourceType", ressourceType);
		model.addAttribute("listRessource", ressource);
		return "listRessource";
	}

	// Récupérer le contenu de la ressource par son id
	@GetMapping("/ressource/{id}")
	public String afficherRessource(@PathVariable Long id, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		Optional<Ressource> ressource = _ressourceService.findById(id);
		if (ressource.isPresent()) {
			List<String> paragraphs = extractParagraphs(ressource.get().getContent());

			if (user != null) {
				model.addAttribute("myUser", user);

				boolean isFavorite = _favoriteService.getFavoriteByUserAndRessourceId(user.getId(), id).isPresent();
				model.addAttribute("isFavorite", isFavorite);

				boolean isExploit = _exploitService.getExploitByUserAndRessourceId(user.getId(), id).isPresent();
				model.addAttribute("isExploit", isExploit);

				boolean isSaveToConsult = _saveToConsultService.getSaveToConsultByUserAndRessourceId(user.getId(), id)
						.isPresent();
				model.addAttribute("isSaveToConsult", isSaveToConsult);

				List<Comment> comments = _commentService.getCommentsWithReplies(id);
				model.addAttribute("comments", comments);

			}

			model.addAttribute("paragraphs", paragraphs);
			model.addAttribute("ressource", ressource.get());
			model.addAttribute("relationTypes", _relationTypeService.findAll());
			model.addAttribute("ressourceTypes", _ressourceTypeService.findAll());
			model.addAttribute("categories", _categoryService.findAll());
			return "ressource";
		}

		return "redirect:/home";
	}

	@GetMapping("/ressource/edit/{id}")
	public String openEditForm(@PathVariable Long id, Model model) {
		Optional<Ressource> ressource = _ressourceService.findById(id);
		if (ressource.isPresent()) {
			List<String> paragraphs = extractParagraphs(ressource.get().getContent());
			model.addAttribute("title", "Modification d'une ressource");
			model.addAttribute("paragraphs", paragraphs);
			model.addAttribute("ressource", ressource.get());
			model.addAttribute("relationTypes", _relationTypeService.findAll());
			model.addAttribute("ressourceTypes", _ressourceTypeService.findAll());
			model.addAttribute("categories", _categoryService.findAll());
			return "ressourceForm";
		}
		return "redirect:/home"; // Redirige si l'ID n'existe pas
	}

	@PostMapping("/saveRessource")
	public String saveOrUpdateRessource(@Valid Ressource ressource, BindingResult result,
			@RequestParam List<String> paragraphs, @RequestParam(required = false) MultipartFile imageFile,
			@RequestParam Long categoryId, @RequestParam Long ressourceTypeId, @RequestParam List<Long> relationTypeIds,
			Model model) {

		boolean isCreation = (ressource.getId() == null);

		// Vérifie les erreurs de validation de formulaire
		if (result.hasErrors()) {
			model.addAttribute("title", isCreation ? "Création d'une ressource" : "Modification d'une ressource");
			model.addAttribute("relationTypes", _relationTypeService.findAll());
			model.addAttribute("ressourceTypes", _ressourceTypeService.findAll());
			model.addAttribute("categories", _categoryService.findAll());
			model.addAttribute("paragraphs", paragraphs);
			return "ressourceForm";
		}

		// Association de la catégorie et du type de ressource
		ressource.setCategory(_categoryService.findById(categoryId).orElse(null));
		ressource.setRessourceType(_ressourceTypeService.findById(ressourceTypeId).orElse(null));

		// --- Gestion de l'image ---
		if (imageFile != null && !imageFile.isEmpty()) {
			String basePath = new File("src/main/resources/static/uploads").getAbsolutePath();
			String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
			String filePath = Paths.get(basePath, uniqueFileName).toString();

			File directory = new File(basePath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			try {
				imageFile.transferTo(new File(filePath));
				ressource.setHeaderImagePath("/uploads/" + uniqueFileName);
			} catch (Exception e) {
				result.rejectValue("headerImagePath", "upload.failed", "Le fichier n'a pas pu être enregistré.");
				model.addAttribute("title", isCreation ? "Création d'une ressource" : "Modification d'une ressource");
				model.addAttribute("relationTypes", _relationTypeService.findAll());
				model.addAttribute("ressourceTypes", _ressourceTypeService.findAll());
				model.addAttribute("categories", _categoryService.findAll());
				model.addAttribute("paragraphs", paragraphs);
				return "ressourceForm";
			}
		} else if (isCreation) {
			// Si c'est une création, l'image est obligatoire
			result.rejectValue("headerImagePath", "missing.file", "L'image est obligatoire pour une création.");
			model.addAttribute("title", "Création d'une ressource");
			model.addAttribute("relationTypes", _relationTypeService.findAll());
			model.addAttribute("ressourceTypes", _ressourceTypeService.findAll());
			model.addAttribute("categories", _categoryService.findAll());
			model.addAttribute("paragraphs", paragraphs);
			return "ressourceForm";
		}

		// Transformation des paragraphes
		String content = paragraphs.stream().filter(p -> !p.trim().isEmpty()).map(p -> "<section>" + p + "</section>")
				.collect(Collectors.joining(""));
		ressource.setContent(content);

		// Sauvegarde principale
		ressource.setUpdateDate(Date.valueOf(LocalDate.now()));
		var newRessource = _ressourceService.save(ressource);

		// --- Mise à jour des types de relation ---
		List<HaveRelationType> existingRelations = _haveRelationTypeService
				.getRessourceRelationsTypes(newRessource.getId());
		Set<Long> existingTypeIds = existingRelations.stream().map(r -> r.getRelationType().getId())
				.collect(Collectors.toSet());
		Set<Long> selectedTypeIds = new HashSet<>(relationTypeIds);

		// Ajouter les nouveaux
		for (Long typeId : selectedTypeIds) {
			if (!existingTypeIds.contains(typeId)) {
				_relationTypeService.findById(typeId).ifPresent(relationType -> {
					HaveRelationType rel = new HaveRelationType(newRessource, relationType);
					_haveRelationTypeService.save(rel);
				});
			}
		}

		// Supprimer ceux qui ne sont plus cochés
		for (HaveRelationType relation : existingRelations) {
			if (!selectedTypeIds.contains(relation.getRelationType().getId())) {
				_haveRelationTypeService.delete(relation);
			}
		}

		return "redirect:/ressource/" + newRessource.getId();
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

	@GetMapping("/commenter/{ressourceId}")
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

	@GetMapping("/commenter/reponse/{commentId}")
	public String repondreAuCommentaire(@PathVariable Long commentId, @RequestParam String content,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		Optional<Comment> parentComment = _commentService.FindById(commentId);

		if (parentComment.isPresent()) {
			Comment reply = new Comment();
			reply.setContent(content);
			reply.setName(user.getFirstName()); // Ou autre
			reply.setParent(parentComment.get()); // Il faut que ton entity `Comment` ait un champ `parent`

			_commentService.save(reply);
			return "redirect:/ressource/" + parentComment.get().getRessource().getId();
		} else {
			return "redirect:/home";
		}
	}

}
