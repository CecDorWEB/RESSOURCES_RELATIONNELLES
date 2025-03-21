package com.RESSOURCES_RELATIONNELLES.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.services.RelationTypeService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;

@Controller
public class RessourceController {

	@Autowired
	private RessourceService ressourceService;

	@Autowired
	private RelationTypeService relationTypeService;

	@GetMapping("/ressource")
	public String consultAllRessources(Model model) {
		List<RelationType> relationType = relationTypeService.getAllRelationType();
		List<Ressource> ressource = ressourceService.getAllRessources();
		model.addAttribute("listRelation", relationType);
		model.addAttribute("listRessource", ressource);
		return "listRessource";
	}
}
