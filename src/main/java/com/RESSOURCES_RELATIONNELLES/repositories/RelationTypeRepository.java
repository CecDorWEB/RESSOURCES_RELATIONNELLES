package com.RESSOURCES_RELATIONNELLES.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.RelationType;

@Repository
public interface RelationTypeRepository extends JpaRepository<RelationType, Long> {
    boolean existsByName(String name);
}
