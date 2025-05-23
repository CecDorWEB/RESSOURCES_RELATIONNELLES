package com.RESSOURCES_RELATIONNELLES.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.RessourceType;

@Repository
public interface RessourceTypeRepository extends JpaRepository<RessourceType, Long> {
    boolean existsByName(String name);
}
