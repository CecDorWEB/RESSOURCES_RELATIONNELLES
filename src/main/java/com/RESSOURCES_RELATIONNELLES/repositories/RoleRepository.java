package com.RESSOURCES_RELATIONNELLES.repositories;

import com.RESSOURCES_RELATIONNELLES.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
