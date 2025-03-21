package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.repositories.RelationTypeRepository;

@Service
public class RelationTypeService extends BaseService<RelationType, Long> {
	@Autowired
	private RelationTypeRepository relationTypeRepository;

	protected RelationTypeService(JpaRepository<RelationType, Long> baseRepository) {
		super(baseRepository);
	}

	public List<RelationType> getAllRelationType() {
		return relationTypeRepository.findAll();
	}

}
