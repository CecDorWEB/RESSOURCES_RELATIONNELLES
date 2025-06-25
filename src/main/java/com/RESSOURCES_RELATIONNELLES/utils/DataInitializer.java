package com.RESSOURCES_RELATIONNELLES.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.RESSOURCES_RELATIONNELLES.entities.Category;
import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.entities.RessourceType;
import com.RESSOURCES_RELATIONNELLES.entities.Role;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.*;

import com.RESSOURCES_RELATIONNELLES.repositories.CategoryRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RelationTypeRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceTypeRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RoleRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RessourceTypeRepository _ressourceTypeRepository;
    private final CategoryRepository _categoryRepository;
    private final RelationTypeRepository _relationTypeRepository;
    private final RoleRepository _roleRepository;
    private final UserRepository _userRepository;

    public DataInitializer(RessourceTypeRepository ressourceTypeRepository, CategoryRepository categoryRepository, RelationTypeRepository relationTypeRepository, RoleRepository roleRepository, UserRepository userRepository) {
        _ressourceTypeRepository = ressourceTypeRepository;
        _categoryRepository = categoryRepository;
        _relationTypeRepository = relationTypeRepository;
        _roleRepository = roleRepository;
        _userRepository = userRepository;
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
            if(!_roleRepository.existsByName("MEMBER"))
                _roleRepository.save(new Role("MEMBER", "Rôle de base pour les utilisateurs inscrits sur la plateforme.", null));
            if(!_roleRepository.existsByName("MODERATOR"))
                _roleRepository.save(new Role("MODERATOR", "Rôle ayant accès à la modération des ressources et des commentaires.", null));
            if(!_roleRepository.existsByName("ADMINISTRATOR"))
                _roleRepository.save(new Role("ADMINISTRATOR", "Rôle ayant accès à l’administration et aux statistiques.", null));
            if(!_roleRepository.existsByName("SUPER-ADMINISTRATOR"))
                _roleRepository.save(new Role("SUPER-ADMINISTRATOR", "Rôle ayant tous les droits, y compris la gestion des utilisateurs.", null));
        }
        
        if (_userRepository.count() == 0) {
            Role moderatorRole = _roleRepository.findByName("MODERATOR");
            Role adminRole = _roleRepository.findByName("ADMINISTRATOR");
            Role superAdminRole = _roleRepository.findByName("SUPER-ADMINISTRATOR");

            Date today = Date.valueOf(LocalDate.of(2025, 4, 13));

            if (moderatorRole != null) {
                User moderator = new User();
                moderator.setFirstName("MODERATOR");
                moderator.setLastName("MODERATOR");
                moderator.setEmail("moderator@hotmail.fr");
                moderator.setPassword("MODERATOR");
                moderator.setGender('F');
                moderator.setBirthday(Date.valueOf(LocalDate.of(2005, 2, 1)));
                moderator.setCreationDate(today);
                moderator.setActived(true);
                moderator.setRole(moderatorRole);
                _userRepository.save(moderator);
            }

            if (adminRole != null) {
                User admin = new User();
                admin.setFirstName("ADMINISTRATOR");
                admin.setLastName("ADMINISTRATOR");
                admin.setEmail("administrator@hotmail.fr");
                admin.setPassword("ADMINISTRATOR");
                admin.setGender('M');
                admin.setBirthday(Date.valueOf(LocalDate.of(2005, 3, 1)));
                admin.setCreationDate(today);
                admin.setActived(true);
                admin.setRole(adminRole);
                _userRepository.save(admin);
            }

            if (superAdminRole != null) {
                User superAdmin = new User();
                superAdmin.setFirstName("SUPER-ADMINISTRATOR");
                superAdmin.setLastName("SUPER-ADMINISTRATOR");
                superAdmin.setEmail("super-administrator@hotmail.fr");
                superAdmin.setPassword("SUPER-ADMINISTRATOR");
                superAdmin.setGender('M');
                superAdmin.setBirthday(Date.valueOf(LocalDate.of(2005, 4, 1)));
                superAdmin.setCreationDate(today);
                superAdmin.setActived(true);
                superAdmin.setRole(superAdminRole);
                _userRepository.save(superAdmin);
            }
        }
	}
}
