package com.RESSOURCES_RELATIONNELLES.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.SaveToConsult;

@Repository
public interface saveToConsultRepository extends JpaRepository<SaveToConsult, Long> {

	@Query("SELECT stc.ressource.id FROM SaveToConsult stc WHERE stc.user.id= :userId")
	List<Long> findSaveToConsultByUserId(@Param("userId") Long userId);

	@Query("SELECT stc FROM SaveToConsult stc WHERE stc.user.id= :userId AND stc.ressource.id= :ressourceId")
	Optional<SaveToConsult> findIfIsSaveToConsult(@Param("userId") Long userId,@Param("ressourceId") Long ressourceId);
}
