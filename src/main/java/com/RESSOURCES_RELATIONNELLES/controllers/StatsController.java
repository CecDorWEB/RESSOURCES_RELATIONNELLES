package com.RESSOURCES_RELATIONNELLES.controllers;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.Statistic;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceRepository;
import com.RESSOURCES_RELATIONNELLES.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatsController {

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/stats")
    public String showStats(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (!userService.isAdmin(user)) {
            return "redirect:/";
        }
        // Récupération de toutes les ressources
        List<Ressource> ressources = ressourceRepository.findAll();

        // Maps pour stocker les stats par catégorie
        Map<String, Integer> consultationsByCategory = new LinkedHashMap<>();
        Map<String, Integer> favorisByCategory = new LinkedHashMap<>();
        Map<String, Integer> exploitationsByCategory = new LinkedHashMap<>();

        // Variables globales
        int totalConsultations = 0;
        int totalFavoris = 0;
        int totalExploitations = 0;
        int totalCommentaires = 0;

        // Pour trouver la “top resource” la plus consultée, favorite etc
        Ressource topConsultRessource = null;
        Ressource topFavoriRessource = null;
        Ressource topExploitRessource = null;

        for (Ressource r : ressources) {
            Statistic stat = r.getStatistic();
            String categoryName = (r.getCategory() != null) ? r.getCategory().getName() : "Sans catégorie";

            int consult = (stat != null) ? stat.getNbConsult() : 0;
            int fav = (stat != null) ? stat.getNbFav() : 0;
            int exploit = (stat != null) ? stat.getNbExploit() : 0;
            int comment = (stat != null) ? stat.getNbComment() : 0;

            // On incrémente les stats pour la catégorie
            consultationsByCategory.put(categoryName,
                    consultationsByCategory.getOrDefault(categoryName, 0) + consult);
            favorisByCategory.put(categoryName,
                    favorisByCategory.getOrDefault(categoryName, 0) + fav);
            exploitationsByCategory.put(categoryName,
                    exploitationsByCategory.getOrDefault(categoryName, 0) + exploit);

            // On calcule les totaux
            totalConsultations += consult;
            totalFavoris += fav;
            totalExploitations += exploit;
            totalCommentaires += comment;

            // Recherche de la ressource top consultations
            if (topConsultRessource == null || consult > topConsultRessource.getStatistic().getNbConsult()) {
                topConsultRessource = r;
            }
            // Recherche de la ressource top favoris
            if (topFavoriRessource == null || fav > topFavoriRessource.getStatistic().getNbFav()) {
                topFavoriRessource = r;
            }
            // Recherche de la ressource top exploitations
            if (topExploitRessource == null || exploit > topExploitRessource.getStatistic().getNbExploit()) {
                topExploitRessource = r;
            }
        }

        // Passage des données à la vue
        model.addAttribute("ressources", ressources);

        // Labels et data pour Chart.js
        model.addAttribute("consultLabels", consultationsByCategory.keySet());
        model.addAttribute("consultData", consultationsByCategory.values());

        model.addAttribute("favLabels", favorisByCategory.keySet());
        model.addAttribute("favData", favorisByCategory.values());

        model.addAttribute("exploitLabels", exploitationsByCategory.keySet());
        model.addAttribute("exploitData", exploitationsByCategory.values());

        // Totaux
        model.addAttribute("totalConsultations", totalConsultations);
        model.addAttribute("totalFavoris", totalFavoris);
        model.addAttribute("totalExploitations", totalExploitations);
        model.addAttribute("totalCommentaires", totalCommentaires);

        // Top ressources (vérifie qu’elles ne sont pas null pour éviter un NPE)
        model.addAttribute("topConsultResource", topConsultRessource);
        model.addAttribute("topFavoriResource", topFavoriRessource);
        model.addAttribute("topExploitResource", topExploitRessource);

        return "admin/stats";
    }

    @GetMapping("/admin/stats/export")
    public void exportCSV(HttpServletResponse response) throws IOException {

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=stats.csv");

        PrintWriter writer = new PrintWriter(response.getOutputStream(), true, java.nio.charset.StandardCharsets.UTF_8);
        writer.write('\uFEFF'); // BOM UTF-8 pour compatibilité csv

        // En-têtes de colonnes
        writer.println("Titre;Catégorie;Consultations;Favoris;Exploitations;Commentaires");

        List<Ressource> ressources = ressourceRepository.findAll();

        for (Ressource r : ressources) {
            String titre = r.getTitle().replace(";", " ");
            String categorie = r.getCategory() != null ? r.getCategory().getName().replace(";", " ") : "-";
            int consult = r.getStatistic() != null ? r.getStatistic().getNbConsult() : 0;
            int fav = r.getStatistic() != null ? r.getStatistic().getNbFav() : 0;
            int exploit = r.getStatistic() != null ? r.getStatistic().getNbExploit() : 0;
            int comment = r.getStatistic() != null ? r.getStatistic().getNbComment() : 0;

            // Utilisation du point-virgule comme séparateur
            writer.printf("%s;%s;%d;%d;%d;%d%n", titre, categorie, consult, fav, exploit, comment);
        }

        writer.flush();
        writer.close();
    }

}
