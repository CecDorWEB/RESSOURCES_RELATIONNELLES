package com.RESSOURCES_RELATIONNELLES.controllers;

import org.springframework.ui.Model;
import com.RESSOURCES_RELATIONNELLES.entities.Comment;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.repositories.CommentRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ModerationController {

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/moderator")
    public String showModerationPage(Model model) {
        List<Ressource> ressources = ressourceRepository.findByIsPublishedFalse();
        List<Comment> comments = commentRepository.findAll(); // ou findByIsReportedTrue()

        model.addAttribute("ressources", ressources);
        model.addAttribute("comments", comments);

        return "moderator";
    }
    @GetMapping("/moderator/ressource/{id}")
    public String viewRessource(@PathVariable Long id, Model model) {
        Ressource ressource = ressourceRepository.findById(id).orElse(null);
        model.addAttribute("ressource", ressource);
        return "moderator/view-ressource"; // à créer
    }

    @PostMapping("/moderator/ressource/{id}/publish")
    public String publishRessource(@PathVariable Long id) {
        Ressource ressource = ressourceRepository.findById(id).orElse(null);
        if (ressource != null) {
            ressource.setIsPublished(true);
            ressourceRepository.save(ressource);
        }
        return "redirect:/moderator";
    }

    @GetMapping("/moderator/comment/{id}")
    public String viewComment(@PathVariable Long id, Model model) {
        Comment comment = commentRepository.findById(id).orElse(null);
        model.addAttribute("comment", comment);
        return "moderator/view-comment"; // à créer
    }

    @PostMapping("/moderator/comment/{id}/suspend")
    public String suspendComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setActivated(false);
            commentRepository.save(comment);
        }
        return "redirect:/moderator";
    }


    @PostMapping("/moderator/comment/{id}/activate")
    public String activateComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setActivated(true);
            commentRepository.save(comment);
        }
        return "redirect:/moderator";
    }

}
