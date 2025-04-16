package com.RESSOURCES_RELATIONNELLES.utils;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.RESSOURCES_RELATIONNELLES.entities.Category;
import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.entities.RessourceType;
import com.RESSOURCES_RELATIONNELLES.entities.Role;
import com.RESSOURCES_RELATIONNELLES.repositories.*;

import com.RESSOURCES_RELATIONNELLES.repositories.CategoryRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RelationTypeRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceTypeRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RessourceTypeRepository _ressourceTypeRepository;
    private final CategoryRepository _categoryRepository;
    private final RelationTypeRepository _relationTypeRepository;
    private final RoleRepository _roleRepository;

    public DataInitializer(RessourceTypeRepository ressourceTypeRepository, CategoryRepository categoryRepository, RelationTypeRepository relationTypeRepository, RoleRepository roleRepository) {
        _ressourceTypeRepository = ressourceTypeRepository;
        _categoryRepository = categoryRepository;
        _relationTypeRepository = relationTypeRepository;
        _roleRepository = roleRepository;
    }

	@Override
	public void run(String... args) {

		if (_categoryRepository.count() == 0) {
			List<String> ressourceCategories = List.of("Communication", "Cultures", "Développement personnel",
					"Intelligence émotionnelle", "Loisirs", "Monde professionnel", "Parentalité", "Qualité de vie",
					"Recherche de sens", "Santé physique", "Santé psychique", "Spiritualité", "Vie affective");

			for (String name : ressourceCategories) {
				if (!_categoryRepository.existsByName(name)) {
					_categoryRepository.save(new Category(name, null));
				}
			}
		}

		if (_ressourceTypeRepository.count() == 0) {

			List<String> ressourceTypes = List.of("Activité / Jeu à réaliser", "Article", "Carte défi",
					"Cours au format PDF", "Exercice / Atelier", "Fiche de lecture", "Jeu en ligne", "Vidéo");

			for (String name : ressourceTypes) {
				if (!_ressourceTypeRepository.existsByName(name)) {
					_ressourceTypeRepository.save(new RessourceType(name, null));
				}
			}
		}

		if (_relationTypeRepository.count() == 0) {

			List<String> relationTypes = List.of("Soi", "Conjoints", "Famille", "Professionnelle",
					"Amis et communautés", "Inconnus");

			for (String name : relationTypes) {
				if (!_relationTypeRepository.existsByName(name)) {
					_relationTypeRepository.save(new RelationType(name, null));
				}
			}

        }

        if(_roleRepository.count() == 0) {
            if(!_roleRepository.existsByName("Utilisateur"))
                _roleRepository.save(new Role("Utilisateur", "Rôle de base pour les utilisateurs inscrits sur la plateforme.", null));
            if(!_roleRepository.existsByName("Utilisateur"))
                _roleRepository.save(new Role("Modérateur", "Rôle ayant accès à la modération des ressources et des commentaires.", null));
            if(!_roleRepository.existsByName("Utilisateur"))
                _roleRepository.save(new Role("Administrateur", "Rôle ayant accès à l’administration et aux statistiques.", null));
            if(!_roleRepository.existsByName("Utilisateur"))
                _roleRepository.save(new Role("Super-Administrateur", "Rôle ayant tous les droits, y compris la gestion des utilisateurs.", null));
        }
    }
}
