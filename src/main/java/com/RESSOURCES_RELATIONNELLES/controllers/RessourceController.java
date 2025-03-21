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

import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.services.RelationTypeService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;

import jakarta.validation.Valid;

@Controller
public class RessourceController {
	private final RessourceService _ressourceService;

	public RessourceController(RessourceService ressourceService) {
		this._ressourceService = ressourceService;
	}

    @GetMapping("/ressource/create")
    public String openCreateForm(Model model) {
        model.addAttribute("title", "Création d'une ressource");
        model.addAttribute("ressource", new Ressource());
        return "ressourceForm";
    }

	@GetMapping("/ressources")
	public String consultAllRessources(Model model) {
		List<RelationType> relationType = _relationTypeService.getAllRelationType();
		List<Ressource> ressource = _ressourceService.getAllRessources();
		model.addAttribute("listRelation", relationType);
		model.addAttribute("listRessource", ressource);
		return "listRessource";
	}

	@GetMapping("/ressource/{id}")
	public String afficherRessource(@PathVariable Long id, Model model) {
		Ressource ressource = _ressourceService.getRessourceById(id);
		if(ressource != null)
		{
			model.addAttribute("title", "Modification d'une ressource");
            model.addAttribute("paragraphs", paragraphs);
            model.addAttribute("ressource", ressource.get());
            return "ressourceForm";
        }
        return "redirect:/home"; // Redirige si l'ID n'existe pas
    }

	@GetMapping("/ressource/edit/{id}")
	public String openEditForm(@PathVariable Long id, Model model) {
		Optional<Ressource> ressource = _ressourceService.FindById(id);
		if (ressource.isPresent()) {

			List<String> paragraphs = extractParagraphs(ressource.get().getContent());

			model.addAttribute("title", "Modification d'une ressource");
			model.addAttribute("paragraphs", paragraphs);
			model.addAttribute("ressource", ressource.get());
			return "ressource-form";
		}
		return "redirect:/home"; // Redirige si l'ID n'existe pas
	}

	@PostMapping("/saveRessource")
	public String saveOrUpdateRessource(@Valid Ressource ressource, BindingResult result,
			@RequestParam List<String> paragraphs, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("title",
					ressource.getId() == null ? "Création d'une ressource" : "Modification d'une ressource");
			return "ressource-form";
		}

		// Transformation des paragraphes en <section>
		String content = paragraphs.stream().filter(p -> !p.trim().isEmpty()).map(p -> "<section>" + p + "</section>")
				.collect(Collectors.joining(""));

		ressource.setContent(content);

		var newRessource = _ressourceService.SaveRessource(ressource);

		if (newRessource != null && newRessource.getId() > 0) 
		{
			return "redirect:/ressource/" + newRessource.getId();
		} 
		else 
		{
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
				.toList(); // Convertir en liste
	}
}
