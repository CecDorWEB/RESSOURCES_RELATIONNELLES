package com.RESSOURCES_RELATIONNELLES.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.RESSOURCES_RELATIONNELLES.entities.Favorite;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.services.FavoriteService;
import com.RESSOURCES_RELATIONNELLES.services.RelationTypeService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;
import com.RESSOURCES_RELATIONNELLES.services.RessourceTypeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
	
	private final RessourceService _ressourceService;
	private final FavoriteService _favoriteService;

	public FavoriteController(RessourceService ressourceService, FavoriteService favoriteService) {
		this._ressourceService = ressourceService;
		this._favoriteService = favoriteService;
	}

	@PostMapping("/create")
	public String addFavorite(@RequestParam("ressourceId") Long ressourceId, HttpSession session) {
		
		User user = (User) session.getAttribute("user");

	    if (user == null) {
	        return "redirect:/home";
	    }
	    
	    Optional<Ressource> optionalRessource = _ressourceService.FindById(ressourceId);
	    Ressource ressource = optionalRessource.get();
	    
	    //Vérifier si la ressource est déjà en favorie
	    boolean isFavorite = _favoriteService.getFavoriteByUserAndRessourceId(user.getId(), ressourceId).isPresent();
	   
	    if(isFavorite) {
	    	_favoriteService.deleteFavoriteByUserAndRessource(user.getId(), ressourceId);
	    } else {
	    
	    Favorite favorite = new Favorite();
	    favorite.setUser(user);
	    favorite.setRessource(ressource);

	    _favoriteService.save(favorite);
	    
	    }
	    return "redirect:/ressource/"+ressourceId ;
	}
	
}
