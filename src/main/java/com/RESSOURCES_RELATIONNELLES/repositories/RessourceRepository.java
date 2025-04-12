package com.RESSOURCES_RELATIONNELLES.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {
	
	@Query("SELECT res FROM Ressource res WHERE isPublished = true AND isActived=true AND status='public'")
	List<Ressource> findAllRessourcesActivedAndPublished();

	@Query("SELECT res FROM Ressource res " + "LEFT JOIN res.listRelationTypes rel "
			+ "WHERE (:relationTypeId IS NULL OR rel.relationType.id = :relationTypeId) "
			+ "AND (:searchWord IS NULL OR LOWER(res.title) LIKE LOWER(CONCAT('%', :searchWord, '%')) "
			+ "OR LOWER(res.description) LIKE LOWER(CONCAT('%', :searchWord, '%')))")
	List<Ressource> findByFilters(@Param("relationTypeId") Long relationTypeId, @Param("searchWord") String searchWord);
}
