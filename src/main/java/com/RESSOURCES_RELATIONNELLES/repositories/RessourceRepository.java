package com.RESSOURCES_RELATIONNELLES.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

	@Query("select res from Ressource res JOIN res.listRelationTypes rel WHERE rel.id = :relationTypeId ")
	public List<Ressource> findAllRessourceByRelationType(@Param("relationTypeId") Long relationTypeId);
}
