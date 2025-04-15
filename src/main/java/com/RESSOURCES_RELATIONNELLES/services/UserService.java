package com.RESSOURCES_RELATIONNELLES.services;

import com.RESSOURCES_RELATIONNELLES.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService extends BaseService<User, Long> {


    protected UserService(JpaRepository<User, Long> baseRepository) {
        super(baseRepository);
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
                "Super-Administrateur".equalsIgnoreCase(user.getRole().getName())
                ||
                "Dieu".equalsIgnoreCase(user.getRole().getName());

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
                "Administrateur".equalsIgnoreCase(user.getRole().getName())
                ||
                "Super-Administrateur".equalsIgnoreCase(user.getRole().getName())
                 ||
                "Dieu".equalsIgnoreCase(user.getRole().getName());


    }
}
