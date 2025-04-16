package com.RESSOURCES_RELATIONNELLES.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.Favorite;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.User;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	@Query("SELECT fav FROM Favorite fav Where fav.user.id=:userId")
	List<Favorite> findAllFavoriteByUserId(@Param("userId") Long userId);
	
	@Query("SELECT fav.ressource.id FROM Favorite fav Where fav.user.id=:userId")
	List<Long> findFavoriteIdByUserId(@Param("userId") Long userId);
	
	@Query("SELECT fav FROM Favorite fav Where fav.user.id=:userId and fav.ressource.id=:ressourceId")
	Optional<Favorite> findIfIsFavorite(@Param("userId") Long userId,
			@Param("ressourceId") Long ressourceId);
	
}
