package com.RESSOURCES_RELATIONNELLES.services;

import com.RESSOURCES_RELATIONNELLES.entities.HaveRelationType;
import com.RESSOURCES_RELATIONNELLES.repositories.HaveRelationTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HaveRelationTypeService extends BaseService<HaveRelationType, Long> {
    private final HaveRelationTypeRepository _haveRelationTypeRepository;
    protected HaveRelationTypeService(HaveRelationTypeRepository haveRelationTypeRepository) {
        super(haveRelationTypeRepository);
        _haveRelationTypeRepository = haveRelationTypeRepository;
    }

    public List<HaveRelationType> getRessourceRelationsTypes(Long ressourceId) {
        return _haveRelationTypeRepository.getRessourceRelationsTypes(ressourceId);
    }
}
