package com.RESSOURCES_RELATIONNELLES.services;

import java.util.List;
import java.util.Optional;

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

	public Optional<Comment> FindById(Long id) {
		return _commentRepository.findById(id);
	}

	@Autowired
	private CommentRepository commentRepository;

	public List<Comment> getCommentsWithReplies(Long ressourceId) {
		List<Comment> comments = commentRepository.findByRessourceId(ressourceId);
		// Filtrer les réponses imbriquées, c'est-à-dire ajouter les réponses à leurs
		// parents
		comments.forEach(comment -> {
			List<Comment> replies = commentRepository.findByParent(comment);
			comment.setReplies(replies); // Ajoute les réponses au commentaire
		});
		return comments;
	}

	// Méthode pour signaler un commentaire (mettre à jour le champ isReported)
	public void signalCommentaire(Long id) {
		Optional<Comment> optionalComment = commentRepository.findById(id);
		if (optionalComment.isPresent()) {
			Comment comment = optionalComment.get();
			comment.setReported(true); // Met à jour le champ isReported à true
			commentRepository.save(comment); // Sauvegarde le commentaire avec isReported = true
		} else {
			throw new RuntimeException("Commentaire non trouvé");
		}
	}

}
