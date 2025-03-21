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
}
