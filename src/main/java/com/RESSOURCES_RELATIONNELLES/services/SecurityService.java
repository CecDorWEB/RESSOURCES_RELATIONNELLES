package com.RESSOURCES_RELATIONNELLES.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.RESSOURCES_RELATIONNELLES.entities.Role;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.RoleRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class SecurityService {

	private final HttpSession session;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;

    private static final String AUTH_TOKEN = "IsUserConnectedToken";

	public SecurityService(HttpSession session, UserRepository userRepository, PasswordEncoder passwordEncoder,
			RoleRepository roleRepository) {
		this.session = session;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

    public boolean isAuthenticated() {
        return Boolean.TRUE.equals(session.getAttribute(AUTH_TOKEN));
    }


    public void setAuthToken(){
        if (!isAuthenticated()){
            session.setAttribute(AUTH_TOKEN, true);
        }
    }

    public void removeAuthToken(){
        if (isAuthenticated()){
            session.invalidate();
        }
    }

	public boolean isBanned(String email) {
		User user = userRepository.findByEmail(email);

        if (user != null) {
            return !user.isActived();
        }

        return false;
    }

	// ✅ Vérifie si un utilisateur existe déjà en base
	public boolean userAlreadyExists(String email) {
		return userRepository.findByEmail(email) != null;
	}

	public boolean login(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			setAuthToken();
			session.setAttribute("user", user); // ✅ AJOUT INDISPENSABLE
			return true;
		}
		return false;
	}

	public boolean signUpUser(User user) {
		if (!userAlreadyExists(user.getEmail())) {
			// 🛡️ Attribution d’un rôle par défaut
			Role defaultRole = roleRepository.findByName("Utilisateur");
			if (defaultRole == null) {
				throw new RuntimeException("Rôle par défaut 'Utilisateur' introuvable en base !");
			}
			user.setRole(defaultRole);

			// 🔒 Encodage sécurisé du mot de passe
			String hashedPassword = passwordEncoder.encode(user.getPassword());
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
