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

    // Mise à jour groupée du rôle
    @PostMapping("/users/batch-update-role")
    public String batchUpdateRole(@RequestParam("userIds") List<Long> userIds,
                                  @RequestParam("newRoleId") Long roleId,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");

        if (!isSuperAdmin(user)) {
            ra.addFlashAttribute("error", "Accès refusé.");
            return "redirect:/";
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé"));

        List<User> users = userRepository.findAllById(userIds);
        for (User u : users) {
            u.setRole(role);
        }

        userRepository.saveAll(users);

        ra.addFlashAttribute("success", "Rôles mis à jour !");
        return "redirect:/superadmin/users";
    }
}
