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



    public boolean hasAccess(Long idUser, String expectedRole) {
        // Récupère l'utilisateur en BDD
        User user = userRepository.findById(idUser).orElse(null);

        if (user == null || user.getRole() == null) {
            return false;
        }

        String userRoleName = user.getRole().getName();

        return expectedRole.equalsIgnoreCase(userRoleName);
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
            session.invalidate();
        }
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
}
