package com.RESSOURCES_RELATIONNELLES.controllers;

import com.RESSOURCES_RELATIONNELLES.entities.Role;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.RoleRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Vérifie que l'utilisateur connecté est Super-Administrateur
    private boolean isSuperAdmin(User user) {
        System.out.println("👤 Utilisateur en session : " + user);
        if (user != null && user.getRole() != null) {
            System.out.println("🔐 Rôle trouvé : " + user.getRole().getName());
        } else {
            System.out.println("❌ Aucun rôle ou utilisateur null");
        }

        return user != null &&
                user.getRole() != null &&
                "Super-Administrateur".equalsIgnoreCase(user.getRole().getName());
    }


    // Liste complète des utilisateurs
    @GetMapping("/users")
    public String manageUsers(@RequestParam(required = false) Long roleId,
                              Model model,
                              HttpSession session,
                              RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");

        if (!isSuperAdmin(user)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        List<User> users = (roleId != null) ?
                userRepository.findByRoleId(roleId) :
                userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("selectedRoleId", roleId);
        return "superadmin-users";
    }

    // Mise à jour du rôle d’un utilisateur
    @PostMapping("/users/update-role")
    public String updateUserRole(@RequestParam Long userId,
                                 @RequestParam Long roleId,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");

        if (!isSuperAdmin(user)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        User userToUpdate = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findById(roleId).orElseThrow();
        userToUpdate.setRole(role);
        userRepository.save(userToUpdate);
        ra.addFlashAttribute("success", "Rôle de l'utilisateur mis à jour !");
        return "redirect:/superadmin/users";
    }

    // Toggle activation
    @GetMapping("/users/toggle-active/{id}")
    public String toggleActivation(@PathVariable Long id,
                                   HttpSession session,
                                   RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");

        if (!isSuperAdmin(user)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        User userToToggle = userRepository.findById(id).orElseThrow();
        userToToggle.setActived(!userToToggle.isActived());
        userRepository.save(userToToggle);
        String msg = userToToggle.isActived() ? "Utilisateur réactivé." : "Utilisateur suspendu.";
        ra.addFlashAttribute("success", msg);
        return "redirect:/superadmin/users";
    }

    @PostMapping("/users/batch-update-role")
    public String batchUpdateRole(@RequestParam(value = "userIds", required = false) List<Long> userIds,
                                  @RequestParam("newRoleId") Long roleId,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");

        if (!isSuperAdmin(user)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        if (userIds == null || userIds.isEmpty()) {
            ra.addFlashAttribute("error", "Aucun utilisateur sélectionné !");
            return "redirect:/superadmin/users";
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé"));

        List<User> users = userRepository.findAllById(userIds);
        for (User u : users) {
            u.setRole(role);
        }

        userRepository.saveAll(users);

        ra.addFlashAttribute("success", "Rôles mis à jour avec succès !");
        return "redirect:/superadmin/users";
    }


    // Affiche le formulaire de création d’un compte
    @GetMapping("/users/create")
    public String showCreateUserForm(Model model, HttpSession session, RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("user");
        if (!isSuperAdmin(currentUser)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "superadmin/create-user";
    }

    // Traitement du formulaire
    @PostMapping("/users/create")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam("roleId") Long roleId,
                             RedirectAttributes ra,
                             HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (!isSuperAdmin(currentUser)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            ra.addFlashAttribute("error", "Un compte avec cet email existe déjà !");
            return "redirect:/superadmin/users/create";
        }


        Role selectedRole = roleRepository.findById(roleId).orElseThrow();
        user.setRole(selectedRole);

        // hash du mot de passe
        user.setPassword(org.springframework.security.crypto.bcrypt.BCrypt.hashpw(user.getPassword(), org.springframework.security.crypto.bcrypt.BCrypt.gensalt()));

        userRepository.save(user);
        ra.addFlashAttribute("success", "Utilisateur créé avec succès !");
        return "redirect:/superadmin/users";
    }
    // Affiche le formulaire d’édition d’un utilisateur
    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id,
                                   Model model,
                                   HttpSession session,
                                   RedirectAttributes ra) {
        // Vérifie que le user connecté est un super-admin
        User currentUser = (User) session.getAttribute("user");
        if (!isSuperAdmin(currentUser)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        // Récupère l'utilisateur à modifier
        User userToEdit = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Utilisateur introuvable"));

        // Prépare la liste des rôles pour le <select> dans le formulaire
        List<Role> roles = roleRepository.findAll();

        // Passe les données au modèle
        model.addAttribute("user", userToEdit);
        model.addAttribute("roles", roles);

        // Retourne la vue Thymeleaf (ton HTML pour "Modifier un utilisateur")
        return "superadmin/edit-user";
    }

    /**
     * Traite le formulaire d’édition
     *
     * Remarque : on récupère l’ID dans l’URL et un objet User
     *            comme “binding” pour les champs du formulaire.
     *            On gère également l’option de modifier (ou non)
     *            le mot de passe via “newPassword”.
     */
    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id,
                           @ModelAttribute("user") User formUser,
                           @RequestParam("roleId") Long roleId,
                           @RequestParam(value = "newPassword", required = false) String newPassword,
                           HttpSession session,
                           RedirectAttributes ra) {

        // Vérifie que le user connecté est un super-admin
        User currentUser = (User) session.getAttribute("user");
        if (!isSuperAdmin(currentUser)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        // On récupère l'utilisateur actuel dans la BDD
        User userToUpdate = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Utilisateur introuvable"));

        // Mets à jour les champs (Nom, Prénom, Email, etc.)
        userToUpdate.setFirstName(formUser.getFirstName());
        userToUpdate.setLastName(formUser.getLastName());
        userToUpdate.setEmail(formUser.getEmail());

        // Si un nouveau mot de passe est spécifié, on le hash et on l’enregistre
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            String hashedPassword = org.springframework.security.crypto.bcrypt.BCrypt
                    .hashpw(newPassword, org.springframework.security.crypto.bcrypt.BCrypt.gensalt());
            userToUpdate.setPassword(hashedPassword);
        }

        // Récupère le rôle sélectionné et l’assigne
        Role newRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rôle introuvable"));
        userToUpdate.setRole(newRole);

        // Sauvegarde des modifications
        userRepository.save(userToUpdate);

        // Message de confirmation
        ra.addFlashAttribute("success", "Les informations de l’utilisateur ont été mises à jour !");
        return "redirect:/superadmin/users";
    }
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             HttpSession session,
                             RedirectAttributes ra) {
        // Vérifie que le user connecté est un super-admin
        User currentUser = (User) session.getAttribute("user");
        if (!isSuperAdmin(currentUser)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        // On supprime l'utilisateur
        userRepository.deleteById(id);

        // Message de confirmation
        ra.addFlashAttribute("success", "Utilisateur supprimé avec succès !");
        return "redirect:/superadmin/users";
    }

}
