package com.RESSOURCES_RELATIONNELLES.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.RESSOURCES_RELATIONNELLES.services.saveToConsultService;

import jakarta.servlet.http.HttpSession;

import com.RESSOURCES_RELATIONNELLES.entities.SaveToConsult;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;

@Controller
@RequestMapping("/savetoconsult")
public class SaveToConsultController {

	private final RessourceService _ressourceService;
	private final saveToConsultService _saveToConsultService;
	
	public SaveToConsultController(RessourceService ressourceService, saveToConsultService saveToConsultService) {
		this._ressourceService = ressourceService;
		this._saveToConsultService = saveToConsultService;
	}
	
	@PostMapping("/create")
	public String addSaveToConsult(@RequestParam("ressourceId") Long ressourceId, HttpSession session) {
		
		User user = (User) session.getAttribute("user");

	    if (user == null) {
	        return "redirect:/home";
	    }
	    
	    Optional<Ressource> optionalRessource = _ressourceService.FindById(ressourceId);
	    Ressource ressource = optionalRessource.get();
	    
	    //Vérifier si la ressource est déjà dans les ressources saveToConsult
	    boolean isSaveToConsult = _saveToConsultService.getSaveToConsultByUserAndRessourceId(user.getId(), ressourceId).isPresent();
	   
	    if(isSaveToConsult) {
	    	_saveToConsultService.deleteSaveToConsultByUserAndRessource(user.getId(), ressourceId);
	    } else {
	    
	    SaveToConsult saveToConsult = new SaveToConsult();
	    saveToConsult.setUser(user);
	    saveToConsult.setRessource(ressource);

	    _saveToConsultService.save(saveToConsult);
	    
	    }
	    return "redirect:/ressource/"+ressourceId ;
	}
}
