package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceRepository;

@Service
public class RessourceService {

	private final RessourceRepository _resourceRepository;

	public List<Ressource> getAllRessources() {
		return _resourceRepository.findAll();
	}

	public Ressource getRessourceById(Long id) {
		return _resourceRepository.findById(id).orElse(null);
	}

	public RessourceService(RessourceRepository resourceRepository) {
		this._resourceRepository = resourceRepository;
	}

	public Ressource SaveRessource(Ressource ressource) {
		return _resourceRepository.save(ressource);
	}

	public Optional<Ressource> FindById(Long id) {
		return _resourceRepository.findById(id);
	}

	public void DeleteRessource(Ressource ressource) {
		_resourceRepository.delete(ressource);
	}

}
