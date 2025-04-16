package com.RESSOURCES_RELATIONNELLES.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service // ✅ Ajout de l'annotation @Service pour que Spring puisse gérer ce service
public class SecurityService {

	private final HttpSession session;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private static final String AUTH_TOKEN = "IsUserConnectedToken"; // ✅ Uniformisation du token

	public SecurityService(HttpSession session, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.session = session;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// ✅ Vérifie si l'utilisateur est connecté
	public boolean isAuthenticated() {
		return Boolean.TRUE.equals(session.getAttribute(AUTH_TOKEN));
	}

	// ✅ Définit le token d'authentification
	public void setAuthToken() {
		if (!isAuthenticated()) {
			session.setAttribute(AUTH_TOKEN, true);
		}
	}

	// ✅ Supprime le token d'authentification
	public void removeAuthToken() {
		if (isAuthenticated()) {
			session.removeAttribute(AUTH_TOKEN);
		}
	}

	// ✅ Vérifie si un utilisateur existe déjà en base
	public boolean userAlreadyExists(String email) {
		return userRepository.findByEmail(email) != null;
	}

	// ✅ Vérifie les identifiants pour la connexion
	public boolean login(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			session.setAttribute("userEmail", email); // 🆕 on garde l’email
			setAuthToken(); // on met le token
			return true;
		}
		return false;
	}

	// ✅ Inscription d'un nouvel utilisateur
	public boolean signUpUser(User user) {
		if (!userAlreadyExists(user.getEmail())) {
			String hashedPassword = passwordEncoder.encode(user.getPassword()); // Encodage sécurisé
			user.setPassword(hashedPassword);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public User getCurrentUser() {
		Object email = session.getAttribute("userEmail"); // tu dois stocker ça lors de la connexion
		if (email != null) {
			return userRepository.findByEmail((String) email);
		}
		return null;
	}

}
