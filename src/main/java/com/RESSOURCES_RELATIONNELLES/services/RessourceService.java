package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceRepository;

@Service
public class RessourceService extends BaseService<Ressource, Long> {

	protected RessourceService(JpaRepository<Ressource, Long> baseRepository) {
		super(baseRepository);
	}


}
