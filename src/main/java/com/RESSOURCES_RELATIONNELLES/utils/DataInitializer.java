package com.RESSOURCES_RELATIONNELLES.utils;

import com.RESSOURCES_RELATIONNELLES.entities.Category;
import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import com.RESSOURCES_RELATIONNELLES.entities.RessourceType;
import com.RESSOURCES_RELATIONNELLES.repositories.CategoryRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RelationTypeRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.RessourceTypeRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RessourceTypeRepository _ressourceTypeRepository;
    private final CategoryRepository _categoryRepository;
    private final RelationTypeRepository _relationTypeRepository;

    public DataInitializer(RessourceTypeRepository ressourceTypeRepository, CategoryRepository categoryRepository, RelationTypeRepository relationTypeRepository) {
        _ressourceTypeRepository = ressourceTypeRepository;
        _categoryRepository = categoryRepository;
        _relationTypeRepository = relationTypeRepository;
    }

    @Override
    public void run(String... args) {
        if (_ressourceTypeRepository.count() == 0) {
            List<String> ressourceCategories = List.of(
                    "Communication",
                    "Cultures",
                    "Développement personnel",
                    "Intelligence émotionnelle",
                    "Loisirs",
                    "Monde professionnel",
                    "Parentalité",
                    "Qualité de vie",
                    "Recherche de sens",
                    "Santé physique",
                    "Santé psychique",
                    "Spiritualité",
                    "Vie affective"
            );

            for (String name : ressourceCategories) {
                if (!_categoryRepository.existsByName(name)) {
                    _categoryRepository.save(new Category(name, null));
                }
            }


            List<String> ressourceTypes = List.of(
                    "Activité / Jeu à réaliser",
                    "Article",
                    "Carte défi",
                    "Cours au format PDF",
                    "Exercice / Atelier",
                    "Fiche de lecture",
                    "Jeu en ligne",
                    "Vidéo" );

            for (String name : ressourceTypes) {
                if (!_ressourceTypeRepository.existsByName(name)) {
                    _ressourceTypeRepository.save(new RessourceType(name, null));
                }
            }


            List<String> relationTypes = List.of(
                    "Soi",
                    "Conjoints",
                    "Famille",
                    "Professionnelle",
                    "Amis et communautés",
                    "Inconnus"
            );

            for (String name : relationTypes) {
                if (!_relationTypeRepository.existsByName(name)) {
                    _relationTypeRepository.save(new RelationType(name, null));
                }
            }

        }
    }
}

