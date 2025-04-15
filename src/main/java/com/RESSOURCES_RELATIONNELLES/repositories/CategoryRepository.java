package com.RESSOURCES_RELATIONNELLES.repositories;

import com.RESSOURCES_RELATIONNELLES.entities.Category;
import com.RESSOURCES_RELATIONNELLES.entities.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
