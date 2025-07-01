package org.example.gestionchampionnatthymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.gestionchampionnatthymeleaf.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
