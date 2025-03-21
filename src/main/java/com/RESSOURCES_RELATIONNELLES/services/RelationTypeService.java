package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.repositories.RelationTypeRepository;

@Service
public class RelationTypeService {
	@Autowired
	private RelationTypeRepository relationTypeRepository;

	public List<RelationType> getAllRelationType() {
		return relationTypeRepository.findAll();
	}
}
