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

    public User getUserByEmail(String email) { return _repository.findByEmail(email); }
}
