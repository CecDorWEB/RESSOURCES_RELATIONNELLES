package com.RESSOURCES_RELATIONNELLES.services;

import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService extends BaseService<User, Long> {

    private final UserRepository _repository;

    protected UserService(JpaRepository<User, Long> baseRepository, UserRepository repository) {
        super(repository);
        _repository = repository;
    }

    public boolean isSuperAdmin(User user) {
        System.out.println("👤 Utilisateur en session : " + user);
        if (user != null && user.getRole() != null) {
            System.out.println("🔐 Rôle trouvé : " + user.getRole().getName());
        } else {
            System.out.println("❌ Aucun rôle ou utilisateur null");
        }

        return user != null &&
                user.getRole() != null &&
                "SUPER-ADMINISTRATOR".equalsIgnoreCase(user.getRole().getName());

    }

    public boolean isAdmin(User user){
        System.out.println("👤 Utilisateur en session : " + user);
        if (user != null && user.getRole() != null) {
            System.out.println("🔐 Rôle trouvé : " + user.getRole().getName());
        } else {
            System.out.println("❌ Aucun rôle ou utilisateur null");
        }

        return user != null &&
                user.getRole() != null &&
                "ADMINISTRATOR".equalsIgnoreCase(user.getRole().getName())
                ||
                "SUPER-ADMINISTRATOR".equalsIgnoreCase(user.getRole().getName());


    }

    public User getUserByEmail(String email) { return _repository.findByEmail(email); }
}
