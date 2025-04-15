package com.RESSOURCES_RELATIONNELLES.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.Favorite;
import com.RESSOURCES_RELATIONNELLES.repositories.FavoriteRepository;

@Service
public class FavoriteService extends BaseService<Favorite, Long> {

	@Autowired
	private FavoriteRepository _favoriteRepository;
	
	protected FavoriteService(JpaRepository<Favorite, Long> baseRepository) {
		super(baseRepository);
	}
}

