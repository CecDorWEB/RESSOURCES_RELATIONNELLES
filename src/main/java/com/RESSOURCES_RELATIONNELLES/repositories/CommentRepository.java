package com.RESSOURCES_RELATIONNELLES.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAll(); // ou findByIsReportedTrue() si tu veux que les signalés

	List<Comment> findByIsReportedTrue();

	List<Comment> findByRessourceId(Long ressourceId);

	List<Comment> findByParent(Comment parent); // Récupérer les réponses d'un commentaire

}
