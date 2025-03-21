package com.RESSOURCES_RELATIONNELLES.controllers;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/ressource/edit/{id}")
    public String openEditForm(@PathVariable Long id, Model model) {
        Optional<Ressource> ressource = _ressourceService.FindById(id);
        if (ressource.isPresent()) {

            List<String> paragraphs = extractParagraphs(ressource.get().getContent());

            model.addAttribute("title", "Modification d'une ressource");
            model.addAttribute("paragraphs", paragraphs);
            model.addAttribute("ressource", ressource.get());
            return "ressourceForm";
        }
        return "redirect:/home"; // Redirige si l'ID n'existe pas
    }

    @PostMapping("/saveRessource")
    public String saveOrUpdateRessource(
            @Valid Ressource ressource,
            BindingResult result,
            @RequestParam List<String> paragraphs,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("title", ressource.getId() == null ? "Création d'une ressource" : "Modification d'une ressource");
            return "ressourceForm";
        }

        // Transformation des paragraphes en <section>
        String content = paragraphs.stream()
                .filter(p -> !p.trim().isEmpty())
                .map(p -> "<section>" + p + "</section>")
                .collect(Collectors.joining(""));

        ressource.setContent(content);

        _ressourceService.SaveRessource(ressource);

        return "redirect:/home";
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
