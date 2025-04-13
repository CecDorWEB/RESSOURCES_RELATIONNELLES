package com.RESSOURCES_RELATIONNELLES.controllers;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class StatsController {

    @Autowired
    private RessourceRepository ressourceRepository;

    @GetMapping("/admin/stats")
    public String showStats(Model model) {
        List<Ressource> ressources = ressourceRepository.findAll();

        Map<String, Integer> consultationsByCategory = new LinkedHashMap<>();
        Map<String, Integer> favorisByCategory = new LinkedHashMap<>();

        for (Ressource res : ressources) {
            String categoryName = (res.getCategory() != null) ? res.getCategory().getName() : "Sans catégorie";
            consultationsByCategory.put(categoryName,
                    consultationsByCategory.getOrDefault(categoryName, 0)
                            + ((res.getStatistic() != null) ? res.getStatistic().getNbConsult() : 0));
            favorisByCategory.put(categoryName,
                    favorisByCategory.getOrDefault(categoryName, 0)
                            + ((res.getStatistic() != null) ? res.getStatistic().getNbFav() : 0));
        }

        System.out.println("CONSULT LABELS = " + consultationsByCategory.keySet());
        System.out.println("CONSULT DATA = " + consultationsByCategory.values());

        model.addAttribute("consultLabels", consultationsByCategory.keySet());
        model.addAttribute("consultData", consultationsByCategory.values());
        model.addAttribute("favLabels", favorisByCategory.keySet());
        model.addAttribute("favData", favorisByCategory.values());
        model.addAttribute("ressources", ressources);

        return "admin/stats";
    }

    @GetMapping("/admin/stats/export")
    public void exportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=stats.csv");

        List<Ressource> ressources = ressourceRepository.findAll();
        PrintWriter writer = response.getWriter();
        writer.println("Titre,Categorie,Consultations,Favoris,Exploitations,Commentaires");

        for (Ressource r : ressources) {
            String category = r.getCategory() != null ? r.getCategory().getName() : "-";
            int consult = r.getStatistic() != null ? r.getStatistic().getNbConsult() : 0;
            int fav = r.getStatistic() != null ? r.getStatistic().getNbFav() : 0;
            int exploit = r.getStatistic() != null ? r.getStatistic().getNbExploit() : 0;
            int comment = r.getStatistic() != null ? r.getStatistic().getNbComment() : 0;

            writer.printf("%s,%s,%d,%d,%d,%d\n", r.getTitle(), category, consult, fav, exploit, comment);
        }

        writer.flush();
        writer.close();
    }
}