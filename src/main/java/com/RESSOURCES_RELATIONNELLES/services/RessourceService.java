package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceRepository;

@Service
public class RessourceService {
	@Autowired
	private RessourceRepository ressourceRepository;

	public List<Ressource> getAllRessources() {
		return ressourceRepository.findAll();
	}
}
