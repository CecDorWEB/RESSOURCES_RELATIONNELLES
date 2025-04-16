package com.RESSOURCES_RELATIONNELLES.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

	@Query("SELECT res FROM Ressource res WHERE res.isPublished = true AND res.isActived=true AND res.status = 'public'")
	List<Ressource> findAllPublicRessourcesActivedAndPublished();

	@Query("SELECT res FROM Ressource res WHERE res.isPublished = true AND res.isActived = true AND (res.status = 'public' OR (res.status = 'private' AND res.user.id = :userId))")
	List<Ressource> findAllRessourcesActivedAndPublished(@Param("userId") Long userId);

	@Query("SELECT res FROM Ressource res " + "LEFT JOIN res.listRelationTypes rel "
			+ "LEFT JOIN res.ressourceType rest "
			+ "WHERE (:relationTypeId IS NULL OR rel.relationType.id = :relationTypeId) "
			+ "AND (:ressourceTypeId IS NULL OR rest.id = :ressourceTypeId) "
			+ "AND (:searchWord IS NULL OR LOWER(res.title) LIKE LOWER(CONCAT('%', :searchWord, '%')) "
			+ "OR LOWER(res.description) LIKE LOWER(CONCAT('%', :searchWord, '%'))) "
			+ "AND res.isPublished = true AND res.isActived = true AND res.status = 'public'")
	List<Ressource> findPublicRessourcesByFilters(@Param("relationTypeId") Long relationTypeId,
			@Param("ressourceTypeId") Long ressourceTypeId, @Param("searchWord") String searchWord);

	@Query("SELECT res FROM Ressource res " + "LEFT JOIN res.listRelationTypes rel "
			+ "LEFT JOIN res.ressourceType rest "
			+ "WHERE (:relationTypeId IS NULL OR rel.relationType.id = :relationTypeId) "
			+ "AND (:ressourceTypeId IS NULL OR rest.id = :ressourceTypeId) "
			+ "AND (:searchWord IS NULL OR LOWER(res.title) LIKE LOWER(CONCAT('%', :searchWord, '%')) "
			+ "OR LOWER(res.description) LIKE LOWER(CONCAT('%', :searchWord, '%'))) "
			+ "AND res.isPublished = true AND res.isActived = true AND res.user.id= :userId")
	List<Ressource> findRessourcesByFilters(@Param("relationTypeId") Long relationTypeId,
			@Param("ressourceTypeId") Long ressourceTypeId, @Param("searchWord") String searchWord,
			@Param("userId") Long userId);

	@Query("""
    SELECT DISTINCT r FROM Ressource r
    LEFT JOIN r.category c
    LEFT JOIN r.user u
    LEFT JOIN r.ressourceType rt
    LEFT JOIN r.listRelationTypes rel
    WHERE
        (:search IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(r.description) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:categoryIds IS NULL OR c.id IN :categoryIds)
        AND (:ressourceTypeIds IS NULL OR rt.id IN :ressourceTypeIds)
        AND (:relationTypeIds IS NULL OR rel.relationType.id IN :relationTypeIds)
        AND r.status = COALESCE(:status, 'Public')
        AND r.isPublished = true AND r.isActived = true
""")
	Page<Ressource> searchPaged(
			@Param("search") String search,
			@Param("categoryIds") List<Long> categoryIds,
			@Param("ressourceTypeIds") List<Long> ressourceTypeIds,
			@Param("relationTypeIds") List<Long> relationTypeIds,
			@RequestParam("status") String status,
			Pageable pageable
	);

}
