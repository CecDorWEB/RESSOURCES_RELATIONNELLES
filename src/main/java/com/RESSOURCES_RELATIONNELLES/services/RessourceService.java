package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceRepository;

@Service
public class RessourceService extends BaseService<Ressource, Long> {

	@Autowired
	private RessourceRepository _resourceRepository;

	protected RessourceService(JpaRepository<Ressource, Long> baseRepository) {
		super(baseRepository);
	}

	public List<Ressource> getAllPublicRessources() {
		return _resourceRepository.findAllPublicRessourcesActivedAndPublished();
	}

	public List<Ressource> getPublicFilteredRessources(Long relationTypeId,Long ressourceTypeId, String searchWord) {
		return _resourceRepository.findPublicRessourcesByFilters(relationTypeId,ressourceTypeId, searchWord);
	}

}
