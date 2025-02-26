package com.RESSOURCES_RELATIONNELLES.services;

import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void setAuthToken(){
        if (!isAuthenticated()){
            session.setAttribute(AUTH_TOKEN, true);
        }
    }

    // ✅ Supprime le token d'authentification
    public void removeAuthToken(){
        if (isAuthenticated()){
            session.removeAttribute(AUTH_TOKEN);
        }
    }

    // ✅ Vérifie si un utilisateur existe déjà en base
    public boolean userAlreadyExists(String email, String username) {
        return userRepository.findByEmail(email) != null || userRepository.findByUsername(username) != null;
    }

    // ✅ Vérifie les identifiants pour la connexion
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) { // Utilisation correcte de matches()
            setAuthToken();
            return true;
        }
        return false;
    }

    // ✅ Inscription d'un nouvel utilisateur
    public boolean signUpUser(User user) {
        if (!userAlreadyExists(user.getEmail(), user.getUsername())) {
            String hashedPassword = passwordEncoder.encode(user.getPassword()); // Encodage sécurisé
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
