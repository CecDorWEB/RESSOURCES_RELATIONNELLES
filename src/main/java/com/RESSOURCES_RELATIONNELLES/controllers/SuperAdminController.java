package com.RESSOURCES_RELATIONNELLES.controllers;

import org.springframework.ui.Model;
import com.RESSOURCES_RELATIONNELLES.entities.Role;
import com.RESSOURCES_RELATIONNELLES.entities.User;
import com.RESSOURCES_RELATIONNELLES.repositories.RoleRepository;
import com.RESSOURCES_RELATIONNELLES.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

        // Liste complète des utilisateurs
        @GetMapping("/users")
        public String manageUsers(@RequestParam(required = false) Long roleId, Model model) {
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
        public String updateUserRole(@RequestParam Long userId, @RequestParam Long roleId, RedirectAttributes ra) {
            User user = userRepository.findById(userId).orElseThrow();
            Role role = roleRepository.findById(roleId).orElseThrow();
            user.setRole(role);
            userRepository.save(user);
            ra.addFlashAttribute("success", "Rôle de l'utilisateur mis à jour !");
            return "redirect:/superadmin/users";
        }

        // Toggle activation
        @GetMapping("/users/toggle-active/{id}")
        public String toggleActivation(@PathVariable Long id, RedirectAttributes ra) {
            User user = userRepository.findById(id).orElseThrow();
            user.setActived(!user.isActived());
            userRepository.save(user);
            String msg = user.isActived() ? "Utilisateur réactivé." : "Utilisateur suspendu.";
            ra.addFlashAttribute("success", msg);
            return "redirect:/superadmin/users";
        }

        @PostMapping("/users/batch-update-role")
        public String batchUpdateRole(@RequestParam("userIds") List<Long> userIds,
                                      @RequestParam("newRoleId") Long roleId) {

            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new IllegalArgumentException("Rôle non trouvé"));

            List<User> users = userRepository.findAllById(userIds);

            for (User user : users) {
                user.setRole(role); // Assure-toi que `setRole()` existe bien
            }

            userRepository.saveAll(users);

            return "redirect:/superadmin/users";
        }


    }



