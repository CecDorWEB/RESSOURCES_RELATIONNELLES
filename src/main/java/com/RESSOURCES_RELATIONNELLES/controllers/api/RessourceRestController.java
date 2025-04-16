package com.RESSOURCES_RELATIONNELLES.controllers.api;

import com.RESSOURCES_RELATIONNELLES.entities.Ressource;
import com.RESSOURCES_RELATIONNELLES.services.RessourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ressource")
public class RessourceRestController {

    private final RessourceService _ressourceService;

    public RessourceRestController(RessourceService ressourceService) {
        _ressourceService = ressourceService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Ressource>> search(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) List<Long> ressourceTypeIds,
            @RequestParam(required = false) List<Long> relationTypeIds,
            @RequestParam(required = false) String status, // 👈 NOUVEAU
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publicationDate").descending());

        Page<Ressource> result = _ressourceService.searchPagedRessources(
                search, categoryIds, ressourceTypeIds, relationTypeIds, status, pageable
        );

        return ResponseEntity.ok(result);
    }

}
