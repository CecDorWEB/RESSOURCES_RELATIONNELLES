package com.RESSOURCES_RELATIONNELLES.repositories;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {
}
