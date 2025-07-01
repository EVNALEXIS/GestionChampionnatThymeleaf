package org.example.gestionchampionnatthymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.gestionchampionnatthymeleaf.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
