package com.RESSOURCES_RELATIONNELLES.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class BaseService<TEntity, TId>  {

    private final JpaRepository<TEntity, TId> _baseRepository;

    protected BaseService(JpaRepository<TEntity, TId> baseRepository) {
        _baseRepository = baseRepository;
    }

    public Optional<TEntity> findById(TId id) {
        return _baseRepository.findById(id);
    }

    public List<TEntity> findAll() {
        return _baseRepository.findAll();
    }

    public TEntity save(TEntity entity) {
        return _baseRepository.save(entity);
    }

    public void delete(TEntity entity) {
        _baseRepository.delete(entity);
    }
}
