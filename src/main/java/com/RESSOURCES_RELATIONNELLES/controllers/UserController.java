package com.RESSOURCES_RELATIONNELLES.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.RESSOURCES_RELATIONNELLES.entities.Exploit;
import com.RESSOURCES_RELATIONNELLES.entities.Favorite;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.SaveToConsult;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import com.RESSOURCES_RELATIONNELLES.services.CommentService;
import com.RESSOURCES_RELATIONNELLES.services.ExploitService;
import com.RESSOURCES_RELATIONNELLES.services.FavoriteService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;
import com.RESSOURCES_RELATIONNELLES.services.saveToConsultService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private RessourceService ressourceService;
	@Autowired
	private CommentService commentService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FavoriteService _favoriteService;

	@Autowired
	private ExploitService _exploitService;

	@Autowired
	private saveToConsultService _saveToConsultService;

	// Formulaire d'ajout
	@GetMapping("/add")
	public String addUserForm(Model model) {
		model.addAttribute("user", new User());
		return "user-form";
	}

	// Soumission du formulaire d'ajout
	@PostMapping("/add")
	public String addUserSubmit(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		userRepository.save(user);
		redirectAttributes.addFlashAttribute("success", "Utilisateur ajouté avec succès !");
		return "redirect:/users";
	}

	// Formulaire de modification

	// Soumission du formulaire de modification

	@GetMapping("/toggle/{id}")
	public String toggleUserActivation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setActived(!user.isActived()); // inverse l'état
			userRepository.save(user);

			String action = user.isActived() ? "réactivé" : "suspendu";
			redirectAttributes.addFlashAttribute("success", "Utilisateur " + action + " avec succès !");
		} else {
			redirectAttributes.addFlashAttribute("error", "Utilisateur introuvable.");
		}

		return "redirect:/users";
	}

	// (Pages futures)
	@GetMapping("/moderator")
	public String moderatorHome() {
		return "moderator";
	}

	@GetMapping("/administrator")
	public String administratorHome() {
		return "administrator";
	}

	@GetMapping("/administrator/gestionRessources")
	public String administraorGestionRessources(Model model) {
		List<Ressource> ressources = ressourceService.findAll();
		model.addAttribute("ressources", ressources);
		return "gestionRessources";
	}

	@GetMapping("/superAdministrator")
	public String superAdministratorHome() {
		return "superAdministrator";
	}

	@GetMapping("/profile")
	public String userProfileHome(Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");

		List<Favorite> favorite = _favoriteService.getAllFavoriteByUserId(user.getId());
		List<SaveToConsult> saveToConsult = _saveToConsultService.getAllSaveToConsultByUserId(user.getId());
		List<Exploit> exploit = _exploitService.findAllExploitByUserId(user.getId());

		model.addAttribute("listFavorite", favorite);
		model.addAttribute("listSaveToConsult", saveToConsult);
		model.addAttribute("listExploit", exploit);

		return "profile";
	}

}
