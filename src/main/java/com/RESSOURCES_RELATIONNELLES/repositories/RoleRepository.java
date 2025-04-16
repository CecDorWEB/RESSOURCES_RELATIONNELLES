package com.RESSOURCES_RELATIONNELLES.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RESSOURCES_RELATIONNELLES.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

	boolean existsByName(String string);
}
