package com.RESSOURCES_RELATIONNELLES.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.Favorite;
import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.FavoriteRepository;

@Service
public class FavoriteService extends BaseService<Favorite, Long> {

	@Autowired
	private FavoriteRepository _favoriteRepository;
	
	protected FavoriteService(JpaRepository<Favorite, Long> baseRepository) {
		super(baseRepository);
	}

	public List<Favorite> getAllFavoriteByUserId(Long userId) {
		return _favoriteRepository.findAllFavoriteByUserId(userId);
	}
	
	public Set<Long> getFavoriteIdByUserId(Long userId) {
		return new HashSet<>(_favoriteRepository.findFavoriteIdByUserId(userId));
	}
	
	public Optional<Favorite> getFavoriteByUserAndRessourceId(Long userId, Long ressourceId) {
		return _favoriteRepository.findIfIsFavorite(userId, ressourceId);
	}
	
	public void deleteFavoriteByUserAndRessource(Long userId, Long ressourceId) {
	    Optional<Favorite> favOpt = _favoriteRepository.findIfIsFavorite(userId, ressourceId);
	    favOpt.ifPresent(_favoriteRepository::delete);
	}
}

