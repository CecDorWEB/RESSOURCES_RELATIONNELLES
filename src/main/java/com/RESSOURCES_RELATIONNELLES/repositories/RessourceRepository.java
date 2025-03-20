package com.RESSOURCES_RELATIONNELLES.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

}
