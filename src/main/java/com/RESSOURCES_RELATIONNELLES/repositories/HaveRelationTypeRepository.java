package com.RESSOURCES_RELATIONNELLES.repositories;

import com.RESSOURCES_RELATIONNELLES.entities.HaveRelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HaveRelationTypeRepository extends JpaRepository<HaveRelationType, Long> {

    @Query("SELECT hrl FROM HaveRelationType hrl " +
            "WHERE (:ressourceId IS NULL OR hrl.ressource.id = :ressourceId)")
    public List<HaveRelationType> getRessourceRelationsTypes(@Param("ressourceId") Long ressourceId);
}
