package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.Comment;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.CommentRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository _commentRepository;

	@Autowired
	UserRepository _userRepository;

	// Méthode pour enregistrer un commentaire
	public Comment save(Comment comment) {
		return _commentRepository.save(comment);
	}

	public List<Comment> getAllCommentsOnlyTrue() {
		return _commentRepository.findByIsReportedTrue();
	}

	public void deleteCommentById(Long commentId) {
		_commentRepository.deleteById(commentId);
	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName(); // l'email est le principal par défaut
		return _userRepository.findByEmail(email); // À condition d’avoir cette méthode dans UserRepository
	}

}
