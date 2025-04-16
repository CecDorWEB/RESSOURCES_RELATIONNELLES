package com.RESSOURCES_RELATIONNELLES.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RESSOURCES_RELATIONNELLES.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email); // 👈 Ajoute cette ligne

	List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
			String firstName, String lastName, String email);

	List<User> findByRoleId(Long roleId);
}
