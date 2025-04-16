package com.RESSOURCES_RELATIONNELLES.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.SaveToConsult;
import com.RESSOURCES_RELATIONNELLES.repositories.saveToConsultRepository;

@Service
public class saveToConsultService extends BaseService<SaveToConsult, Long> {

	@Autowired
	private saveToConsultRepository _saveToConsultRepository;
	
	protected saveToConsultService(JpaRepository<SaveToConsult, Long> baseRepository) {
		super(baseRepository);
	}
	
	public Optional<SaveToConsult> getSaveToConsultByUserAndRessourceId(Long userId, Long ressourceId) {
		return _saveToConsultRepository.findIfIsSaveToConsult(userId, ressourceId);
	}
	
	public void deleteSaveToConsultByUserAndRessource(Long userId, Long ressourceId) {
	    Optional<SaveToConsult> favOpt = _saveToConsultRepository.findIfIsSaveToConsult(userId, ressourceId);
	    favOpt.ifPresent(_saveToConsultRepository::delete);
	}
}