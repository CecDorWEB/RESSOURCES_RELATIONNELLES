package com.RESSOURCES_RELATIONNELLES.services;

import com.RESSOURCES_RELATIONNELLES.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<Category, Long>{

    protected CategoryService(JpaRepository<Category, Long> baseRepository) {
        super(baseRepository);
    }
}
