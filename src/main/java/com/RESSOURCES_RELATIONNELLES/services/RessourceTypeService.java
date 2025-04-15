package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.RessourceType;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceTypeRepository;

@Service
public class RessourceTypeService extends BaseService<RessourceType, Long>{
	@Autowired
	private RessourceTypeRepository ressourceTypeRepository;

	protected RessourceTypeService(JpaRepository<RessourceType, Long> baseRepository) {
		super(baseRepository);
	}

}
