package com.RESSOURCES_RELATIONNELLES.services;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RessourceService {

    private final RessourceRepository _resourceRepository;

    public RessourceService(RessourceRepository resourceRepository) {
        this._resourceRepository = resourceRepository;
    }

    public Ressource SaveRessource(Ressource ressource) {
        return _resourceRepository.save(ressource);
    }

    public Optional<Ressource> FindById(Long id) {
        return _resourceRepository.findById(id);
    }
}
