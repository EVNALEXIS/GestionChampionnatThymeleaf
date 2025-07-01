package org.example.gestionchampionnatthymeleaf.repository;

import org.example.gestionchampionnatthymeleaf.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
