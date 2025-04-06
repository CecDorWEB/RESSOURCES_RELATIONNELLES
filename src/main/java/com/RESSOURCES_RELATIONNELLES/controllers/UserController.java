package com.RESSOURCES_RELATIONNELLES.controllers;

import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// Liste des utilisateurs
	@GetMapping
	public String userHome(@RequestParam(value = "search", required = false) String search, Model model) {
		List<User> users;

		if (search != null && !search.trim().isEmpty()) {
			users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
					search, search, search);
		} else {
			users = userRepository.findAll();
		}

		model.addAttribute("users", users);
		model.addAttribute("search", search); // pour garder la valeur dans le champ
		return "users";
	}

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
	@GetMapping("/edit/{id}")
	public String editUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			model.addAttribute("user", userOptional.get());
			return "user-form";
		} else {
			redirectAttributes.addFlashAttribute("error", "Utilisateur non trouvé.");
			return "redirect:/users";
		}
	}

	// Soumission du formulaire de modification
	@PostMapping("/edit/{id}")
	public String editUserSubmit(@PathVariable Long id, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		user.setId(id); // assure qu'on update l'utilisateur
		userRepository.save(user);
		redirectAttributes.addFlashAttribute("success", "Utilisateur modifié avec succès !");
		return "redirect:/users";
	}

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

	// Suppression d'un utilisateur
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			userRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé avec succès !");
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

	@GetMapping("/superAdministrator")
	public String superAdministratorHome() {
		return "superAdministrator";
	}
}
