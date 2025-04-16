package com.RESSOURCES_RELATIONNELLES.controllers.api;

import com.RESSOURCES_RELATIONNELLES.entities.Category;
import com.RESSOURCES_RELATIONNELLES.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll(); // retourne un List<Category>
    }
}

